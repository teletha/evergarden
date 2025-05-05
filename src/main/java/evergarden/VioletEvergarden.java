/*
 * Copyright (C) 2025 The EVERGARDEN Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package evergarden;

import static javax.tools.Diagnostic.Kind.*;
import static javax.tools.Diagnostic.Kind.OTHER;
import static javax.tools.DocumentationTool.Location.*;
import static javax.tools.JavaFileObject.Kind.*;
import static javax.tools.StandardLocation.*;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ModuleElement;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.Diagnostic.Kind;
import javax.tools.DiagnosticListener;
import javax.tools.DocumentationTool;
import javax.tools.DocumentationTool.DocumentationTask;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import evergarden.design.EvergardenDesignScheme;
import evergarden.host.Hosting;
import evergarden.javadoc.ClassInfo;
import evergarden.javadoc.MethodInfo;
import evergarden.javadoc.SourceCode;
import evergarden.javadoc.TypeResolver;
import evergarden.page.APIPage;
import evergarden.page.ActivityPage;
import evergarden.page.DocumentPage;
import evergarden.web.CodeHighlight;
import icy.manipulator.Icy;
import jdk.javadoc.doclet.Doclet;
import jdk.javadoc.doclet.DocletEnvironment;
import jdk.javadoc.doclet.Reporter;
import kiss.I;
import kiss.Variable;
import kiss.XML;
import psychopath.Directory;
import psychopath.Location;
import psychopath.Locator;
import stylist.StyleDeclarable;
import stylist.Stylist;

/**
 * Abstract base class for configuring and executing the Evergarden Javadoc generation process.
 * This class provides a fluent API (using {@link Icy} annotation for implementation generation)
 * to set up source directories, output location, classpath, project metadata, external
 * documentation links, and finally trigger the documentation build process.
 * <p>
 * The documentation generation involves scanning API source code, document/sample source code,
 * building an internal representation (likely via the {@link Letter} model), and then rendering
 * the final static website (HTML, CSS, JS).
 */
@Icy(modelNamePattern = "(.+)Evergarden")
public abstract class VioletEvergarden {

    /** Stores analyzed class information primarily from document/manual sources. */
    private final List<ClassInfo> docs = new ArrayList();

    /**
     * Maps external package names (e.g., "java.lang") to their corresponding external Javadoc API
     * base URLs.
     */
    private final Map<String, String> externals = new HashMap<>();

    /** Stores package names identified as belonging to the project being documented (internal). */
    private final Set<String> internals = new HashSet<>();

    /**
     * The central data model representing the documentation website being built.
     * It aggregates information from {@link VioletEvergarden} settings and the results
     * of the Doclet processing (API types, document types, samples).
     */
    private final Letter letter = new Letter() {
        /**
         * {@inheritDoc}
         */
        @Override
        public Directory address() {
            return VioletEvergarden.this.address();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String title() {
            return VioletEvergarden.this.title();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String description() {
            return VioletEvergarden.this.description();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Charset encoding() {
            return VioletEvergarden.this.encoding();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Variable<Hosting> authority() {
            return VioletEvergarden.this.host();
        }
    };

    /**
     * Specifies the root directory where the generated documentation website will be outputted.
     * This is a required property.
     *
     * @return The output {@link Directory}.
     */
    @Icy.Property
    public abstract Directory address();

    @Icy.Overload("address")
    private Directory address(String path) {
        return Locator.directory(path);
    }

    @Icy.Overload("address")
    private Directory address(Path path) {
        return Locator.directory(path);
    }

    /**
     * Specifies the list of directories containing the main API source code to be documented.
     * Defaults to an empty list if not specified.
     *
     * @return A list of source code {@link Directory directories}.
     */
    @Icy.Property
    public List<Directory> sources() {
        return List.of();
    }

    @Icy.Overload("sources")
    private List<Directory> sources(String... paths) {
        return I.signal(paths).map(Locator::directory).toList();
    }

    @Icy.Overload("sources")
    private List<Directory> sources(Path... paths) {
        return I.signal(paths).map(Locator::directory).toList();
    }

    /**
     * Specifies the list of directories containing supplementary documents, such as
     * manuals (`*Manual.java`) and test sources (`*Test.java`) used for examples.
     * Defaults to an empty list if not specified.
     *
     * @return A list of document {@link Directory directories}.
     */
    @Icy.Property
    public List<Directory> documents() {
        return List.of();
    }

    @Icy.Overload("documents")
    private List<Directory> documents(String path) {
        return List.of(Locator.directory(path));
    }

    @Icy.Overload("documents")
    private List<Directory> documents(Path path) {
        return List.of(Locator.directory(path));
    }

    /**
     * Specifies the list of directories containing articles (e.g., blog posts, tutorials).
     * Note: The current implementation doesn't seem to explicitly use this property in scanning or
     * building. Defaults to an empty list if not specified.
     *
     * @return A list of article {@link Directory directories}.
     */
    @Icy.Property
    public List<Directory> articles() {
        return List.of();
    }

    @Icy.Overload("articles")
    private List<Directory> articles(String path) {
        return List.of(Locator.directory(path));
    }

    @Icy.Overload("articles")
    private List<Directory> articles(Path path) {
        return List.of(Locator.directory(path));
    }

    /**
     * Specifies the classpath required for compiling and analyzing the source code.
     * This should include all necessary libraries and dependencies.
     * Defaults to null if not specified.
     *
     * @return A list of classpath {@link Location locations} (directories or JARs).
     */
    @Icy.Property
    public List<psychopath.Location> classpath() {
        return null;
    }

    @Icy.Overload("classpath")
    private List<psychopath.Location> classpath(String... paths) {
        return I.signal(paths).map(Locator::locate).toList();
    }

    @Icy.Overload("classpath")
    private List<psychopath.Location> classpath(Path... paths) {
        return I.signal(paths).map(Locator::locate).toList();
    }

    @Icy.Overload("classpath")
    private List<psychopath.Location> classpathBy(List<String> paths) {
        return I.signal(paths).map(Locator::locate).toList();
    }

    /**
     * Specifies the title of the project/product being documented.
     * This is used in the generated website's title and headers.
     * This is a required property.
     *
     * @return The project title string.
     */
    @Icy.Property
    public abstract String title();

    /**
     * Provides a short description or tagline for the project.
     * Used in the generated website metadata or headers.
     * Defaults to an empty string if not specified.
     *
     * @return The project description string.
     */
    @Icy.Property
    public String description() {
        return "";
    }

    /**
     * Specifies the character encoding of the source files.
     * Defaults to the system's default charset. It's recommended to explicitly set this
     * (e.g., to {@code StandardCharsets.UTF_8}).
     *
     * @return The source file {@link Charset}.
     */
    @Icy.Property
    public Charset encoding() {
        return Charset.defaultCharset();
    }

    /**
     * Specifies a listener to receive diagnostic messages (errors, warnings, notes)
     * generated during the documentation process.
     *
     * @return The {@link DiagnosticListener}.
     */
    @Icy.Property
    public DiagnosticListener<? super JavaFileObject> listener() {
        return o -> System.out.println(o);
    }

    @Icy.Overload("listener")
    @SuppressWarnings("unused")
    private DiagnosticListener<? super JavaFileObject> mute() {
        return o -> {
        };
    }

    /**
     * Specify the code repository.
     * 
     * @return
     */
    @Icy.Property
    public Variable<Hosting> host() {
        return Variable.empty();
    }

    @Icy.Overload("host")
    private Variable<Hosting> host(String uri) {
        return Hosting.of(uri);
    }

    /**
     * Configures the documentation build to link against the standard JDK Javadoc API.
     *
     * @return This {@link VioletEvergarden} instance for chaining.
     */
    public final VioletEvergarden useExternalJDKDoc() {
        return useExternalDoc("https://docs.oracle.com/en/java/javase/24/docs/api/");
    }

    /**
     * Configures external Javadoc URLs to resolve types not found in the current project sources.
     * For each valid URL (must start with http and end with "/api/"), it attempts to fetch
     * the "overview-tree.html" to extract package names and associate them with the URL.
     *
     * @param urls A list of base URLs for external Javadoc APIs (e.g.,
     *            "https://some.library/version/api/").
     * @return This {@link VioletEvergarden} instance for chaining.
     */
    public final VioletEvergarden useExternalDoc(String... urls) {
        if (urls != null) {
            for (String url : urls) {
                if (url != null && url.startsWith("http") && url.endsWith("/api/")) {
                    I.http(url + "overview-tree.html", XML.class)
                            .retry(e -> e.delay(Duration.ofMillis(200)).take(20))
                            .flatIterable(xml -> xml.find(".horizontal a"))
                            .waitForTerminate()
                            .to(xml -> {
                                externals.put(xml.text(), url);
                            });
                }
            }
        }
        return this;
    }

    /**
     * Executes the entire documentation generation process based on the current configuration.
     *
     * @return The {@link Letter} object containing the generated documentation model and site
     *         configuration.
     * @throws Error if scanning API or document sources fails critically.
     * @throws RuntimeException wrapping underlying IOExceptions or other errors during the process.
     */
    public final Letter write() {
        Tool.DOLL.set(this);

        // Find all package names in the source directory.
        I.signal(sources()).flatMap(Directory::walkDirectoryWithBase).to(sub -> {
            internals.add(sub.ⅰ.relativize(sub.ⅱ).toString().replace(File.separatorChar, '.'));
        });

        CompletableFuture.allOf(scanDocument(), scanAPI()).join();

        // ========================================================
        // Write resources for the letter (web site)
        // ========================================================
        buildSite(letter);

        return letter;
    }

    /**
     * Scans the specified document directories for manual (`*Manual.java`) and test (`*Test.java`)
     * files using a dedicated Doclet ({@link DocumentDoclet}).
     *
     * @return A {@link CompletableFuture} representing the asynchronous scanning task.
     */
    private CompletableFuture scanDocument() {
        List<Directory> directories = documents();
        if (directories == null || directories.isEmpty()) {
            log("No document/sample directories specified, skipping document scan.");
            return CompletableFuture.completedFuture(null);
        }

        return I.schedule(() -> {
            DocumentationTool tool = ToolProvider.getSystemDocumentationTool();

            try (ToListener listener = new ToListener("violet.document");
                    StandardJavaFileManager m = tool.getStandardFileManager(listener(), Locale.getDefault(), encoding())) {
                m.setLocation(SOURCE_PATH, I.signal(sources()).startWith(directories).map(Directory::asJavaFile).toList());
                m.setLocation(CLASS_PATH, I.signal(classpath()).map(Location::asJavaFile).toList());

                List<JavaFileObject> files = I.signal(m.list(SOURCE_PATH, "", Set.of(SOURCE), true)).take(o -> {
                    String name = o.getName();
                    for (Directory directory : directories) {
                        if (name.startsWith(directory.toString())) {
                            return name.endsWith("Test.java") || name.endsWith("Manual.java");
                        }
                    }
                    return false;
                }).toList();

                if (!files.isEmpty()) {
                    DocumentationTask task = tool.getTask(listener, m, listener(), DocumentDoclet.class, List.of("-package"), files);

                    if (task.call()) {
                        listener().report(new Message(OTHER, "sample", "Succeed in scanning documents."));
                    } else {
                        listener().report(new Message(ERROR, "sample", "Failed in scanning documents."));
                        throw new Error("Fail in scanning sample sources.");
                    }
                }
            } catch (Throwable e) {
                throw I.quiet(e);
            }
        });
    }

    /**
     * Scans the specified main source directories for API documentation using a dedicated Doclet
     * ({@link APIDoclet}).
     *
     * @return A {@link CompletableFuture} representing the asynchronous scanning task.
     */
    private CompletableFuture scanAPI() {
        List<Directory> directories = sources();
        if (directories == null || directories.isEmpty()) {
            log("No API source directories specified, skipping API scan.");
            return CompletableFuture.completedFuture(null);
        }

        return I.schedule(() -> {
            DocumentationTool tool = ToolProvider.getSystemDocumentationTool();

            try (ToListener listener = new ToListener("violet.api");
                    StandardJavaFileManager m = tool.getStandardFileManager(listener(), Locale.getDefault(), encoding())) {
                m.setLocation(SOURCE_PATH, I.signal(directories).map(Directory::asJavaFile).toList());
                m.setLocation(CLASS_PATH, classpath() == null ? null
                        : classpath().stream().map(psychopath.Location::asJavaFile).collect(Collectors.toList()));
                m.setLocationFromPaths(DOCUMENTATION_OUTPUT, List.of(address().create().asJavaPath()));

                Iterable<JavaFileObject> files = m.list(SOURCE_PATH, "", Set.of(SOURCE), true);
                DocumentationTask task = tool.getTask(listener, m, listener(), APIDoclet.class, List.of("-protected"), files);

                if (task.call()) {
                    listener().report(new Message(OTHER, "build", "Succeed in building API."));
                } else {
                    listener().report(new Message(ERROR, "build", "Failed in building API."));
                }
            } catch (Throwable e) {
                throw I.quiet(e);
            }
        });
    }

    /**
     * Builds the final static website content (HTML, CSS, JS, SVG, etc.) into the output directory.
     *
     * @param letter The {@link Letter} model containing all necessary data for the site.
     */
    private void buildSite(Letter letter) {
        SiteBuilder site = SiteBuilder.root(address()).guard("index.html", "main.css", "mocha.html", "mimic.test.js");

        // build CSS
        I.load(VioletEvergarden.class);
        Stylist.pretty()
                .scheme(EvergardenDesignScheme.class)
                .styles(I.findAs(StyleDeclarable.class))
                .formatTo(letter.address().file("main.css").asJavaPath());

        // build JS
        site.build("main.js", VioletEvergarden.class.getResourceAsStream("main.js"));
        site.build("mimic.js", VioletEvergarden.class.getResourceAsStream("mimic.js"));
        site.build("highlight.js", VioletEvergarden.class.getResourceAsStream("highlight.js"), CodeHighlight.build());
        site.buildJSONP("root.js", letter);

        // build SVG
        site.build("main.svg", VioletEvergarden.class.getResourceAsStream("main.svg"));

        // build HTML
        for (ClassInfo info : letter.types) {
            site.buildHTML(new APIPage("api/" + info.id() + ".html", letter, info));
        }
        for (ClassInfo info : docs) {
            site.buildHTML(new DocumentPage("doc/" + info.id() + ".html", letter, info));
        }
        site.buildHTML(new DocumentPage("doc/onepager.html", letter, docs));

        // build change log
        letter.authority().to(repo -> {
            I.http(repo.locateChangeLog(), String.class).waitForTerminate().skipError().to(md -> {
                site.buildHTML(new ActivityPage("doc/changelog.html", letter, repo.getChangeLog(md)));
            });
        });

        // create at last for live reload
        site.buildHTML(new APIPage("index.html", letter, null));
    }

    /**
     * Helper method to report a NOTE level diagnostic message.
     * 
     * @param message The message content.
     */
    private void log(String message) {
        log(Kind.NOTE, "violet", message);
    }

    /**
     * Helper method to report a diagnostic message using the configured listener.
     * 
     * @param kind The kind of diagnostic (ERROR, WARNING, NOTE, etc.).
     * @param code A custom code categorizing the message (e.g., "init", "scan", "site").
     * @param message The detailed message content.
     */
    private void log(Diagnostic.Kind kind, String code, String message) {
        listener().report(new Message(kind, code, message));
    }

    /**
     * A {@link Writer} implementation that forwards written messages (trimmed)
     * as NOTE diagnostics to the main {@link VioletEvergarden#listener()}.
     * Used to capture output from {@link DocumentationTask}.
     */
    private class ToListener extends Writer {

        /** A code used to categorize messages coming from this listener (e.g., "doclet.api"). */
        private final String code;

        /**
         * Creates a new listener with a specific diagnostic code.
         * 
         * @param code The code to associate with messages written to this writer.
         */
        private ToListener(String code) {
            this.code = code;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void write(char[] cbuf, int off, int len) throws IOException {
            String message = new String(cbuf, off, len).trim();
            if (message.length() != 0) {
                log(Kind.NOTE, code, message);
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void flush() throws IOException {
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void close() throws IOException {
        }
    }

    /**
     * A simple implementation of the {@link Diagnostic} interface used for reporting
     * custom messages via the {@link DiagnosticListener}.
     */
    private static class Message implements Diagnostic<JavaFileObject> {

        private final Kind kind;

        private final String code;

        private final String message;

        /**
         * Creates a new diagnostic message.
         * 
         * @param kind The severity (ERROR, WARNING, NOTE, etc.).
         * @param code A category code for the message.
         * @param message The actual message content.
         */
        private Message(Kind kind, String code, String message) {
            this.kind = kind;
            this.code = code;
            this.message = message;
        }

        @Override
        public javax.tools.Diagnostic.Kind getKind() {
            return kind;
        }

        @Override
        public JavaFileObject getSource() {
            return null;
        }

        @Override
        public long getPosition() {
            return Diagnostic.NOPOS;
        }

        @Override
        public long getStartPosition() {
            return Diagnostic.NOPOS;
        }

        @Override
        public long getEndPosition() {
            return Diagnostic.NOPOS;
        }

        @Override
        public long getLineNumber() {
            return Diagnostic.NOPOS;
        }

        @Override
        public long getColumnNumber() {
            return Diagnostic.NOPOS;
        }

        @Override
        public String getCode() {
            return code;
        }

        @Override
        public String getMessage(Locale locale) {
            return message;
        }

        @Override
        public String toString() {
            return kind + ":" + code + ": " + message;
        }
    }

    /**
     * Base class for internal Doclets used by {@link VioletEvergarden}.
     * <p>
     * <strong>DO NOT USE THIS CLASS DIRECTLY.</strong> It is public only due to the
     * limitations of the {@link DocumentationTool} API requiring public Doclet classes.
     * </p>
     * This class handles common Doclet initialization boilerplate and provides access
     * to the parent {@link VioletEvergarden} instance via the {@link Tool} helper.
     */
    private static abstract class BaseDoclet implements Doclet {

        /** Reference to the parent VioletEvergarden configuration instance. */
        protected final VioletEvergarden violet = Tool.useDoll();

        /**
         * {@inheritDoc}
         */
        @Override
        public final void init(Locale locale, Reporter reporter) {
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public final String getName() {
            return getClass().getSimpleName();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public final Set<? extends Option> getSupportedOptions() {
            return Set.of();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public final SourceVersion getSupportedSourceVersion() {
            return SourceVersion.latest();
        }

        /**
         * {@inheritDoc}
         * <p>
         * This is the main entry point for the Doclet execution.
         * </p>
         * 
         * It sets up the {@link Tool} with the current {@link DocletEnvironment}
         * and iterates over the specified elements, dispatching them to the appropriate
         * `process` methods based on their {@link ElementKind}. Finally, it calls the
         * {@link #complete()} hook.
         *
         * @param env The environment providing access to elements, utilities, etc.
         * @return Always true, indicating success (errors should be reported via the listener).
         */
        @Override
        public final boolean run(DocletEnvironment env) {
            // Critical: Setup the Tool helper for this execution thread
            Tool.ENVIRONMENT.set(env);

            for (Element element : env.getSpecifiedElements()) {
                switch (element.getKind()) {
                case MODULE:
                    process((ModuleElement) element);
                    break;

                case PACKAGE:
                    process((PackageElement) element);
                    break;

                default:
                    TypeElement type = (TypeElement) element;
                    process(new ClassInfo(type, new TypeResolver(violet.externals, violet.internals, type)));
                    break;
                }
            }
            complete();

            return true;
        }

        /**
         * Processes a class, interface, enum, record, or annotation type element.
         * Subclasses must implement this to handle the specific logic for these types.
         *
         * @param root A {@link ClassInfo} wrapper around the type element being processed.
         */
        protected abstract void process(ClassInfo root);

        /**
         * Processes a package element.
         * The base implementation does nothing. Subclasses can override if needed.
         *
         * @param root The package element being processed.
         */
        protected void process(PackageElement root) {
        }

        /**
         * Processes a module element.
         * The base implementation does nothing. Subclasses can override if needed.
         *
         * @param root The module element being processed.
         */
        protected void process(ModuleElement root) {
        }

        /**
         * Hook method called after all specified elements have been processed by the `run` method.
         * Subclasses can implement this for finalization tasks (e.g., building relationships).
         */
        protected abstract void complete();
    }

    /**
     * Internal Doclet specifically for processing document and sample source files.
     * <p>
     * <strong>DO NOT USE THIS CLASS DIRECTLY.</strong> It is public only due to Doclet API
     * limitations.
     * </p>
     * It identifies `*Manual.java` files to be treated as primary document pages and extracts
     * code snippets referenced by `@see` tags in test methods (`*Test.java`) as examples (Doodles).
     */
    public static class DocumentDoclet extends BaseDoclet {

        /** The name pattern of document. */
        private static final Pattern DocName = Pattern.compile("(.*)Manual$");

        /**
         * Processes a type element found during the document scan.
         * If it's a public class ending in "Manual", it's added to the main document list.
         * Otherwise, it scans methods for `@see` tags pointing to examples and registers
         * them as {@link Doodle} objects in the {@link Letter}.
         *
         * @param info The {@link ClassInfo} of the type being processed.
         */
        @Override
        protected void process(ClassInfo info) {
            Matcher matcher = DocName.matcher(info.outer().map(o -> o.name).or(""));

            if (matcher.matches() && info.isPublic()) {
                violet.docs.add(0, info);
                violet.log("Found document: " + info.id());
            } else {
                for (MethodInfo method : info.methods()) {
                    if (!method.getSeeTags().isEmpty()) {
                        String code = SourceCode.read(method);
                        for (XML see : method.getSeeTags()) {
                            String[] id = info.identify(see.text());
                            violet.letter.register(new Doodle(id[0], id[1], code, method.contents()));
                            violet.log("Registered doodle from " + info.name + "#" + method.name);
                        }
                    }
                }
            }
        }

        /**
         * Called after processing all document/sample files.
         * Triggers the building of the document navigation tree within the {@link Letter}.
         */
        @Override
        protected void complete() {
            violet.log("Document scan complete. Building document tree...");
            violet.letter.buildDocumentTree(violet.docs);
        }
    }

    /**
     * Internal Doclet specifically for processing API source files.
     * <p>
     * <strong>DO NOT USE THIS CLASS DIRECTLY.</strong> It is public only due to Doclet API
     * limitations.
     * </p>
     * It analyzes each type element encountered and registers its {@link ClassInfo} representation
     * with the main {@link Letter} model.
     */
    public static class APIDoclet extends BaseDoclet {

        /**
         * Processes a type element found during the API scan.
         * Registers the corresponding {@link ClassInfo} with the {@link Letter}.
         *
         * @param info The {@link ClassInfo} of the type being processed.
         */
        @Override
        protected void process(ClassInfo info) {
            violet.letter.register(info);
            violet.log("Found API: " + info.id());
        }

        /**
         * Called after processing all API source files.
         * Triggers the building of type relationships (inheritance, usage) within the
         * {@link Letter}.
         */
        @Override
        protected void complete() {
            violet.log("API scan complete. Building type relationships...");
            violet.letter.buildTypeRelationship();
        }
    }
}