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
import java.util.Comparator;
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
import psychopath.Locator;
import stylist.StyleDeclarable;
import stylist.Stylist;

@Icy
public abstract class AutoMemoriesDollModel {

    /** The name pattern of document. */
    private static final Pattern DocName = Pattern.compile("(.*)Manual$");

    /** The document repository. */
    private final List<ClassInfo> docs = new ArrayList();

    /** PackageName-URL pair. */
    private final Map<String, String> externals = new HashMap();

    /** The internal pacakage names. */
    private final Set<String> internals = new HashSet();

    private final Letter letter = new Letter() {
        /**
         * {@inheritDoc}
         */
        @Override
        public Directory address() {
            return AutoMemoriesDollModel.this.address();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String title() {
            return AutoMemoriesDollModel.this.title();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String description() {
            return AutoMemoriesDollModel.this.description();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Charset charset() {
            return AutoMemoriesDollModel.this.encoding();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Variable<Hosting> authority() {
            return AutoMemoriesDollModel.this.host();
        }
    };

    /**
     * The list of source directories.
     * 
     * @return
     */
    @Icy.Property
    public abstract List<Directory> sources();

    /**
     * The list of source directories.
     * 
     * @return
     */
    @Icy.Overload("sources")
    private List<Directory> sources(String... paths) {
        return I.signal(paths).map(Locator::directory).toList();
    }

    /**
     * The list of source directories.
     * 
     * @return
     */
    @Icy.Overload("sources")
    private List<Directory> sources(Path... paths) {
        return I.signal(paths).map(Locator::directory).toList();
    }

    /**
     * The list of source directories.
     * 
     * @return
     */
    @Icy.Property
    public List<psychopath.Location> classpath() {
        return null;
    }

    /**
     * The list of source directories.
     * 
     * @return
     */
    @Icy.Overload("classpath")
    private List<psychopath.Location> classpath(String... paths) {
        return I.signal(paths).map(Locator::locate).toList();
    }

    /**
     * The list of source directories.
     * 
     * @return
     */
    @Icy.Overload("classpath")
    private List<psychopath.Location> classpath(Path... paths) {
        return I.signal(paths).map(Locator::locate).toList();
    }

    /**
     * The list of source directories.
     * 
     * @return
     */
    @Icy.Overload("classpath")
    private List<psychopath.Location> classpathBy(List<String> paths) {
        return I.signal(paths).map(Locator::locate).toList();
    }

    /**
     * Specify the directory where the product is output.
     * 
     * @return
     */
    @Icy.Property(nullable = true)
    public abstract Directory address();

    /**
     * Specify the directory where the product is output.
     * 
     * @return
     */
    @Icy.Overload("address")
    private Directory address(String path) {
        return Locator.directory(path);
    }

    /**
     * Specify the directory where the product is output.
     * 
     * @return
     */
    @Icy.Overload("address")
    private Directory address(Path path) {
        return Locator.directory(path);
    }

    /**
     * The product name.
     * 
     * @return
     */
    @Icy.Property
    public abstract String title();

    /**
     * The product version.
     * 
     * @return
     */
    @Icy.Property
    public String description() {
        return "";
    }

    /**
     * Specify the directory of samples.
     * 
     * @return
     */
    @Icy.Property
    public List<Directory> samples() {
        return List.of();
    }

    /**
     * Specify the directory of samples.
     * 
     * @return
     */
    @Icy.Overload("samples")
    private List<Directory> samples(String path) {
        return List.of(Locator.directory(path));
    }

    /**
     * Specify the directory of samples.
     * 
     * @return
     */
    @Icy.Overload("samples")
    private List<Directory> samples(Path path) {
        return List.of(Locator.directory(path));
    }

    /**
     * Specify the source encoding.
     * 
     * @return
     */
    @Icy.Property
    public Charset encoding() {
        return Charset.defaultCharset();
    }

    /**
     * Specify the task execution listener.
     * 
     * @return
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
     * Use JDK as the resolvable external document.
     * 
     * @return
     */
    public final AutoMemoriesDoll useExternalJDKDoc() {
        return useExternalDoc("https://docs.oracle.com/en/java/javase/24/docs/api/");
    }

    /**
     * Specifies the URL of the resolvable external document.
     * 
     * @param urls A list of document URL．
     * @return Chainable API.
     */
    public final AutoMemoriesDoll useExternalDoc(String... urls) {
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
        return (AutoMemoriesDoll) this;
    }

    /**
     * Generate documents.
     */
    public final Letter write() {
        synchronized (AutoMemoriesDollModel.class) {
            InternalScanner.model = this;

            long start = System.currentTimeMillis();

            // Find all package names in the source directory.
            I.signal(sources()).flatMap(Directory::walkDirectoryWithBase).to(sub -> {
                internals.add(sub.ⅰ.relativize(sub.ⅱ).toString().replace(File.separatorChar, '.'));
            });

            List<CompletableFuture> futures = new ArrayList();
            DocumentationTool tool = ToolProvider.getSystemDocumentationTool();

            // ========================================================
            // Collect sample source
            // ========================================================
            if (!samples().isEmpty()) {
                futures.add(I.schedule(() -> {
                    try (ToListener listener = new ToListener("sample");
                            StandardJavaFileManager m = tool.getStandardFileManager(listener(), Locale.getDefault(), encoding())) {
                        m.setLocation(SOURCE_PATH, I.signal(sources()).startWith(samples()).map(Directory::asJavaFile).toList());
                        m.setLocation(CLASS_PATH, classpath() == null ? null
                                : classpath().stream().map(psychopath.Location::asJavaFile).collect(Collectors.toList()));

                        List<JavaFileObject> files = I.signal(m.list(SOURCE_PATH, "", Set.of(SOURCE), true))
                                .take(o -> accept(o
                                        .getName()) && (o.getName().endsWith("Test.java") || o.getName().endsWith("Manual.java")))
                                .toList();

                        if (!files.isEmpty()) {
                            DocumentationTask task = tool.getTask(listener, m, listener(), SampleScanner.class, List.of("-package"), files);

                            if (task.call()) {
                                listener().report(new Message(OTHER, "sample", "Succeed in scanning sample sources."));
                            } else {
                                listener().report(new Message(ERROR, "sample", "Fail in scanning sample sources."));
                                throw new Error("Fail in scanning sample sources.");
                            }
                        }
                    } catch (Throwable e) {
                        throw I.quiet(e);
                    }
                }));
            }

            // ========================================================
            // Scan javadoc from main source
            // ========================================================
            futures.add(I.schedule(() -> {
                try (ToListener listener = new ToListener("build");
                        StandardJavaFileManager m = tool.getStandardFileManager(listener(), Locale.getDefault(), encoding())) {
                    m.setLocation(SOURCE_PATH, I.signal(sources()).map(Directory::asJavaFile).toList());
                    m.setLocation(CLASS_PATH, classpath() == null ? null
                            : classpath().stream().map(psychopath.Location::asJavaFile).collect(Collectors.toList()));
                    m.setLocationFromPaths(DOCUMENTATION_OUTPUT, List
                            .of(address() == null ? Path.of("") : address().create().asJavaPath()));

                    DocumentationTask task = tool.getTask(listener, m, listener(), SourceScanner.class, List.of("-protected"), m
                            .list(SOURCE_PATH, "", Set.of(SOURCE), true));

                    if (task.call()) {
                        listener().report(new Message(OTHER, "build", "Succeed in building documents."));
                    } else {
                        listener().report(new Message(ERROR, "build", "Fail in building documents."));
                    }
                } catch (Throwable e) {
                    throw I.quiet(e);
                }
            }));

            CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new)).join();

            complete();

            long end = System.currentTimeMillis();
            System.out.println((end - start));
        }
        return letter;
    }

    private boolean accept(String name) {
        for (Directory directory : samples()) {
            if (name.startsWith(directory.toString())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Build {@link Doclet} to generate documents.
     * 
     * @return
     */
    public final Class<? extends Doclet> writeDoclet() {
        InternalScanner.model = this;

        return SourceScanner.class;
    }

    /**
     * 
     */
    private class ToListener extends Writer {

        private final String code;

        /**
         * @param code
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
                listener().report(new Message(Kind.NOTE, code, message));
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
     * 
     */
    private static class Message implements Diagnostic<JavaFileObject> {

        private final Kind kind;

        private final String code;

        private final String message;

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
     * <h>DONT USE THIS CLASS</h>
     * <p>
     * It is a Doclet for internal use, but it is public because it cannot be made private due to
     * the specifications of the documentation tool.
     * </p>
     */
    private static abstract class InternalScanner implements Doclet {

        /** The setting model. */
        static AutoMemoriesDollModel model;

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
         */
        @Override
        public final boolean run(DocletEnvironment env) {
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
                    process(new ClassInfo(type, new TypeResolver(model.externals, model.internals, type)));
                    break;
                }
            }
            complete();

            return true;
        }

        /**
         * Process a class or interface program element. Provides access to information about the
         * type
         * and its members. Note that an enum type is a kind of class and an annotation type is a
         * kind
         * of interface.
         * 
         * @param root A class or interface program element root.
         */
        protected abstract void process(ClassInfo root);

        /**
         * Process a package program element. Provides access to information about the package and
         * its
         * members.
         * 
         * @param root A package program element root.
         */
        protected void process(PackageElement root) {
        }

        /**
         * Process a module program element. Provides access to information about the module, its
         * directives, and its members.
         * 
         * @param root A module program element root.
         */
        protected void process(ModuleElement root) {
        }

        protected abstract void complete();
    }

    /**
     * <h>DONT USE THIS CLASS</h>
     * <p>
     * It is a Doclet for internal use, but it is public because it cannot be made private due to
     * the specifications of the documentation tool.
     * </p>
     */
    public static class SampleScanner extends InternalScanner {

        /**
         * {@inheritDoc}
         */
        @Override
        protected void process(ClassInfo info) {
            Matcher matcher = DocName.matcher(info.outer().map(o -> o.name).or(""));

            if (matcher.matches() && info.isPublic()) {
                model.docs.add(0, info);
            } else {
                for (MethodInfo method : info.methods()) {
                    if (!method.getSeeTags().isEmpty()) {
                        String code = SourceCode.read(method);
                        for (XML see : method.getSeeTags()) {
                            String[] id = info.identify(see.text());
                            model.letter.register(new Doodle(id[0], id[1], code, method.contents()));
                        }
                    }
                }
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        protected void complete() {
            model.letter.buildDocumentTree(model.docs);
        }
    }

    /**
     * <h>DONT USE THIS CLASS</h>
     * <p>
     * It is a Doclet for internal use, but it is public because it cannot be made private due to
     * the specifications of the documentation tool.
     * </p>
     */
    public static class SourceScanner extends InternalScanner {

        /**
         * {@inheritDoc}
         */
        @Override
        protected void process(ClassInfo info) {
            model.letter.register(info);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        protected void complete() {
            model.letter.buildTypeRelationship();
        }
    }

    protected void complete() {
        // sort data
        letter.modules.sort(Comparator.naturalOrder());
        letter.packages.sort(Comparator.naturalOrder());
        letter.types.sort(Comparator.naturalOrder());

        if (letter.address() != null) {
            SiteBuilder site = I.make(SiteBuilder.class)
                    .root(letter.address())
                    .guard("index.html", "main.css", "mocha.html", "mimic.test.js");

            // build CSS
            I.load(AutoMemoriesDoll.class);
            Stylist.pretty()
                    .scheme(EvergardenDesignScheme.class)
                    .styles(I.findAs(StyleDeclarable.class))
                    .formatTo(letter.address().file("main.css").asJavaPath());

            // build JS
            site.build("main.js", AutoMemoriesDoll.class.getResourceAsStream("main.js"));
            site.build("mimic.js", AutoMemoriesDoll.class.getResourceAsStream("mimic.js"));
            site.build("highlight.js", AutoMemoriesDoll.class.getResourceAsStream("highlight.js"), CodeHighlight.build());

            // build SVG
            site.build("main.svg", AutoMemoriesDoll.class.getResourceAsStream("main.svg"));

            // build HTML
            for (ClassInfo info : letter.types) {
                site.buildHTML(new APIPage("api/" + info.id() + ".html", letter, info));
            }
            for (ClassInfo info : docs) {
                site.buildHTML(new DocumentPage("doc/" + info.id() + ".html", letter, info));
            }

            // build change log
            letter.authority().to(repo -> {
                I.http(repo.locateChangeLog(), String.class).waitForTerminate().skipError().to(md -> {
                    site.buildHTML(new ActivityPage("doc/changelog.html", letter, repo.getChangeLog(md)));
                });
            });

            // create at last for live reload
            site.buildHTML(new APIPage("index.html", letter, null));
        }
    }
}