package evergarden;

import evergarden.Violet;
import evergarden.host.Hosting;
import java.lang.Override;
import java.lang.String;
import java.lang.StringBuilder;
import java.lang.Throwable;
import java.lang.UnsupportedOperationException;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import javax.tools.DiagnosticListener;
import javax.tools.JavaFileObject;
import kiss.Variable;
import psychopath.Directory;
import psychopath.Location;

/**
 * Generated model for {@link VioletEvergarden}.
 * 
 * @see <a href="https://github.com/teletha/icymanipulator">Icy Manipulator (Code Generator)</a>
 */
public class Violet extends VioletEvergarden {

    /**
     * Deceive complier that the specified checked exception is unchecked exception.
     *
     * @param <T> A dummy type for {@link RuntimeException}.
     * @param throwable Any error.
     * @return A runtime error.
     * @throws T Dummy error to deceive compiler.
     */
    private static final <T extends Throwable> T quiet(Throwable throwable) throws T {
        throw (T) throwable;
    }

    /**
     * Create special method invoker.
     *
     * @param name A target method name.
     * @param parameterTypes A list of method parameter types.
     * @return A special method invoker.
     */
    private static final MethodHandle invoker(String name, Class... parameterTypes)  {
        try {
            Method method = evergarden.VioletEvergarden.class.getDeclaredMethod(name, parameterTypes);
            method.setAccessible(true);
            return MethodHandles.lookup().unreflect(method);
        } catch (Throwable e) {
            throw quiet(e);
        }
    }

    /** The overload or intercept method invoker. */
    private static final MethodHandle address$927011984= invoker("address", String.class);

    /** The overload or intercept method invoker. */
    private static final MethodHandle address$478361692= invoker("address", Path.class);

    /** The overload or intercept method invoker. */
    private static final MethodHandle sources$640847889= invoker("sources", String[].class);

    /** The overload or intercept method invoker. */
    private static final MethodHandle sources$1893898243= invoker("sources", Path[].class);

    /** The overload or intercept method invoker. */
    private static final MethodHandle documents$927011984= invoker("documents", String.class);

    /** The overload or intercept method invoker. */
    private static final MethodHandle documents$478361692= invoker("documents", Path.class);

    /** The overload or intercept method invoker. */
    private static final MethodHandle articles$927011984= invoker("articles", String.class);

    /** The overload or intercept method invoker. */
    private static final MethodHandle articles$478361692= invoker("articles", Path.class);

    /** The overload or intercept method invoker. */
    private static final MethodHandle classpath$640847889= invoker("classpath", String[].class);

    /** The overload or intercept method invoker. */
    private static final MethodHandle classpath$1893898243= invoker("classpath", Path[].class);

    /** The overload or intercept method invoker. */
    private static final MethodHandle classpathBy$754143386= invoker("classpathBy", List.class);

    /** The overload or intercept method invoker. */
    private static final MethodHandle mute$1= invoker("mute");

    /** The overload or intercept method invoker. */
    private static final MethodHandle host$927011984= invoker("host", String.class);

    /**
     * Create special property updater.
     *
     * @param name A target property name.
     * @return A special property updater.
     */
    private static final Field updater(String name)  {
        try {
            Field field = Violet.class.getDeclaredField(name);
            field.setAccessible(true);
            return field;
        } catch (Throwable e) {
            throw quiet(e);
        }
    }

    /**
     * Create fast property updater.
     *
     * @param field A target field.
     * @return A fast property updater.
     */
    private static final MethodHandle handler(Field field)  {
        try {
            return MethodHandles.lookup().unreflectSetter(field);
        } catch (Throwable e) {
            throw quiet(e);
        }
    }

    /** The final property updater. */
    private static final Field addressField = updater("address");

    /** The fast final property updater. */
    private static final MethodHandle addressUpdater = handler(addressField);

    /** The final property updater. */
    private static final Field titleField = updater("title");

    /** The fast final property updater. */
    private static final MethodHandle titleUpdater = handler(titleField);

    /** The final property updater. */
    private static final Field sourcesField = updater("sources");

    /** The fast final property updater. */
    private static final MethodHandle sourcesUpdater = handler(sourcesField);

    /** The final property updater. */
    private static final Field documentsField = updater("documents");

    /** The fast final property updater. */
    private static final MethodHandle documentsUpdater = handler(documentsField);

    /** The final property updater. */
    private static final Field articlesField = updater("articles");

    /** The fast final property updater. */
    private static final MethodHandle articlesUpdater = handler(articlesField);

    /** The final property updater. */
    private static final Field classpathField = updater("classpath");

    /** The fast final property updater. */
    private static final MethodHandle classpathUpdater = handler(classpathField);

    /** The final property updater. */
    private static final Field descriptionField = updater("description");

    /** The fast final property updater. */
    private static final MethodHandle descriptionUpdater = handler(descriptionField);

    /** The final property updater. */
    private static final Field encodingField = updater("encoding");

    /** The fast final property updater. */
    private static final MethodHandle encodingUpdater = handler(encodingField);

    /** The final property updater. */
    private static final Field listenerField = updater("listener");

    /** The fast final property updater. */
    private static final MethodHandle listenerUpdater = handler(listenerField);

    /** The final property updater. */
    private static final Field hostField = updater("host");

    /** The fast final property updater. */
    private static final MethodHandle hostUpdater = handler(hostField);

    /** The exposed property. */
    public final Directory address;

    /** The exposed property. */
    public final String title;

    /** The exposed property. */
    public final List<Directory> sources;

    /** The exposed property. */
    public final List<Directory> documents;

    /** The exposed property. */
    public final List<Directory> articles;

    /** The exposed property. */
    public final List<Location> classpath;

    /** The exposed property. */
    public final String description;

    /** The exposed property. */
    public final Charset encoding;

    /** The exposed property. */
    public final DiagnosticListener<? super JavaFileObject> listener;

    /** The exposed property. */
    public final Variable<Hosting> host;

    /**
     * HIDE CONSTRUCTOR
     */
    protected Violet() {
        this.address = null;
        this.title = null;
        this.sources = super.sources();
        this.documents = super.documents();
        this.articles = super.articles();
        this.classpath = super.classpath();
        this.description = super.description();
        this.encoding = super.encoding();
        this.listener = super.listener();
        this.host = super.host();
    }

    /**
     * Specifies the root directory where the generated documentation website will be outputted.
     *  This is a required property.
     * 
     *  @return The output {@link Directory}.
     */
    @Override
    public final Directory address() {
        return this.address;
    }

    /**
     * Provide classic getter API.
     *
     * @return A value of address property.
     */
    @SuppressWarnings("unused")
    private final Directory getAddress() {
        return this.address;
    }

    /**
     * Provide classic setter API.
     *
     * @paran value A new value of address property to assign.
     */
    private final void setAddress(Directory value) {
        if (value == null) {
            throw new IllegalArgumentException("The address property requires non-null value.");
        }
        try {
            addressUpdater.invoke(this, value);
        } catch (UnsupportedOperationException e) {
        } catch (Throwable e) {
            throw quiet(e);
        }
    }

    /**
     * Specifies the title of the project/product being documented.
     *  This is used in the generated website's title and headers.
     *  This is a required property.
     * 
     *  @return The project title string.
     */
    @Override
    public final String title() {
        return this.title;
    }

    /**
     * Provide classic getter API.
     *
     * @return A value of title property.
     */
    @SuppressWarnings("unused")
    private final String getTitle() {
        return this.title;
    }

    /**
     * Provide classic setter API.
     *
     * @paran value A new value of title property to assign.
     */
    private final void setTitle(String value) {
        if (value == null) {
            throw new IllegalArgumentException("The title property requires non-null value.");
        }
        try {
            titleUpdater.invoke(this, value);
        } catch (UnsupportedOperationException e) {
        } catch (Throwable e) {
            throw quiet(e);
        }
    }

    /**
     * Specifies the list of directories containing the main API source code to be documented.
     *  Defaults to an empty list if not specified.
     * 
     *  @return A list of source code {@link Directory directories}.
     */
    @Override
    public final List<Directory> sources() {
        return this.sources;
    }

    /**
     * Provide classic getter API.
     *
     * @return A value of sources property.
     */
    @SuppressWarnings("unused")
    private final List<Directory> getSources() {
        return this.sources;
    }

    /**
     * Provide classic setter API.
     *
     * @paran value A new value of sources property to assign.
     */
    private final void setSources(List<Directory> value) {
        if (value == null) {
            value = super.sources();
        }
        try {
            sourcesUpdater.invoke(this, value);
        } catch (UnsupportedOperationException e) {
        } catch (Throwable e) {
            throw quiet(e);
        }
    }

    /**
     * Specifies the list of directories containing supplementary documents, such as
     *  manuals ("*Manual.java") and test sources ("*Test.java") used for examples.
     *  Defaults to an empty list if not specified.
     * 
     *  @return A list of document {@link Directory directories}.
     */
    @Override
    public final List<Directory> documents() {
        return this.documents;
    }

    /**
     * Provide classic getter API.
     *
     * @return A value of documents property.
     */
    @SuppressWarnings("unused")
    private final List<Directory> getDocuments() {
        return this.documents;
    }

    /**
     * Provide classic setter API.
     *
     * @paran value A new value of documents property to assign.
     */
    private final void setDocuments(List<Directory> value) {
        if (value == null) {
            value = super.documents();
        }
        try {
            documentsUpdater.invoke(this, value);
        } catch (UnsupportedOperationException e) {
        } catch (Throwable e) {
            throw quiet(e);
        }
    }

    /**
     * Specifies the list of directories containing articles (e.g., blog posts, tutorials).
     *  Note: The current implementation doesn't seem to explicitly use this property in scanning or
     *  building. Defaults to an empty list if not specified.
     * 
     *  @return A list of article {@link Directory directories}.
     */
    @Override
    public final List<Directory> articles() {
        return this.articles;
    }

    /**
     * Provide classic getter API.
     *
     * @return A value of articles property.
     */
    @SuppressWarnings("unused")
    private final List<Directory> getArticles() {
        return this.articles;
    }

    /**
     * Provide classic setter API.
     *
     * @paran value A new value of articles property to assign.
     */
    private final void setArticles(List<Directory> value) {
        if (value == null) {
            value = super.articles();
        }
        try {
            articlesUpdater.invoke(this, value);
        } catch (UnsupportedOperationException e) {
        } catch (Throwable e) {
            throw quiet(e);
        }
    }

    /**
     * Specifies the classpath required for compiling and analyzing the source code.
     *  This should include all necessary libraries and dependencies.
     *  Defaults to null if not specified.
     * 
     *  @return A list of classpath {@link Location locations} (directories or JARs).
     */
    @Override
    public final List<Location> classpath() {
        return this.classpath;
    }

    /**
     * Provide classic getter API.
     *
     * @return A value of classpath property.
     */
    @SuppressWarnings("unused")
    private final List<Location> getClasspath() {
        return this.classpath;
    }

    /**
     * Provide classic setter API.
     *
     * @paran value A new value of classpath property to assign.
     */
    private final void setClasspath(List<Location> value) {
        if (value == null) {
            value = super.classpath();
        }
        try {
            classpathUpdater.invoke(this, value);
        } catch (UnsupportedOperationException e) {
        } catch (Throwable e) {
            throw quiet(e);
        }
    }

    /**
     * Provides a short description or tagline for the project.
     *  Used in the generated website metadata or headers.
     *  Defaults to an empty string if not specified.
     * 
     *  @return The project description string.
     */
    @Override
    public final String description() {
        return this.description;
    }

    /**
     * Provide classic getter API.
     *
     * @return A value of description property.
     */
    @SuppressWarnings("unused")
    private final String getDescription() {
        return this.description;
    }

    /**
     * Provide classic setter API.
     *
     * @paran value A new value of description property to assign.
     */
    private final void setDescription(String value) {
        if (value == null) {
            value = super.description();
        }
        try {
            descriptionUpdater.invoke(this, value);
        } catch (UnsupportedOperationException e) {
        } catch (Throwable e) {
            throw quiet(e);
        }
    }

    /**
     * Specifies the character encoding of the source files.
     *  Defaults to the system's default charset. It's recommended to explicitly set this
     *  (e.g., to {@code StandardCharsets.UTF_8}).
     * 
     *  @return The source file {@link Charset}.
     */
    @Override
    public final Charset encoding() {
        return this.encoding;
    }

    /**
     * Provide classic getter API.
     *
     * @return A value of encoding property.
     */
    @SuppressWarnings("unused")
    private final Charset getEncoding() {
        return this.encoding;
    }

    /**
     * Provide classic setter API.
     *
     * @paran value A new value of encoding property to assign.
     */
    private final void setEncoding(Charset value) {
        if (value == null) {
            value = super.encoding();
        }
        try {
            encodingUpdater.invoke(this, value);
        } catch (UnsupportedOperationException e) {
        } catch (Throwable e) {
            throw quiet(e);
        }
    }

    /**
     * Specifies a listener to receive diagnostic messages (errors, warnings, notes)
     *  generated during the documentation process.
     * 
     *  @return The {@link DiagnosticListener}.
     */
    @Override
    public final DiagnosticListener<? super JavaFileObject> listener() {
        return this.listener;
    }

    /**
     * Provide classic getter API.
     *
     * @return A value of listener property.
     */
    @SuppressWarnings("unused")
    private final DiagnosticListener<? super JavaFileObject> getListener() {
        return this.listener;
    }

    /**
     * Provide classic setter API.
     *
     * @paran value A new value of listener property to assign.
     */
    private final void setListener(DiagnosticListener<? super JavaFileObject> value) {
        if (value == null) {
            value = super.listener();
        }
        try {
            listenerUpdater.invoke(this, value);
        } catch (UnsupportedOperationException e) {
        } catch (Throwable e) {
            throw quiet(e);
        }
    }

    /**
     * Specify the code repository.
     *  
     *  @return
     */
    @Override
    public final Variable<Hosting> host() {
        return this.host;
    }

    /**
     * Provide classic getter API.
     *
     * @return A value of host property.
     */
    @SuppressWarnings("unused")
    private final Variable<Hosting> getHost() {
        return this.host;
    }

    /**
     * Provide classic setter API.
     *
     * @paran value A new value of host property to assign.
     */
    private final void setHost(Variable<Hosting> value) {
        if (value == null) {
            value = super.host();
        }
        try {
            hostUpdater.invoke(this, value);
        } catch (UnsupportedOperationException e) {
        } catch (Throwable e) {
            throw quiet(e);
        }
    }

    /**
     * Show all property values.
     *
     * @return All property values.
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("Violet [");
        builder.append("address=").append(address).append(", ");
        builder.append("title=").append(title).append(", ");
        builder.append("sources=").append(sources).append(", ");
        builder.append("documents=").append(documents).append(", ");
        builder.append("articles=").append(articles).append(", ");
        builder.append("classpath=").append(classpath).append(", ");
        builder.append("description=").append(description).append(", ");
        builder.append("encoding=").append(encoding).append(", ");
        builder.append("listener=").append(listener).append(", ");
        builder.append("host=").append(host).append("]");
        return builder.toString();
    }

    /**
     * Generates a hash code for a sequence of property values. The hash code is generated as if all the property values were placed into an array, and that array were hashed by calling Arrays.hashCode(Object[]). 
     *
     * @return A hash value of the sequence of property values.
     */
    @Override
    public int hashCode() {
        return Objects.hash(address, title, sources, documents, articles, classpath, description, encoding, listener, host);
    }

    /**
     * Returns true if the all properties are equal to each other and false otherwise. Consequently, if both properties are null, true is returned and if exactly one property is null, false is returned. Otherwise, equality is determined by using the equals method of the base model. 
     *
     * @return true if the all properties are equal to each other and false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof Violet == false) {
            return false;
        }

        Violet other = (Violet) o;
        if (!Objects.equals(address, other.address)) return false;
        if (!Objects.equals(title, other.title)) return false;
        if (!Objects.equals(sources, other.sources)) return false;
        if (!Objects.equals(documents, other.documents)) return false;
        if (!Objects.equals(articles, other.articles)) return false;
        if (!Objects.equals(classpath, other.classpath)) return false;
        if (!Objects.equals(description, other.description)) return false;
        if (!Objects.equals(encoding, other.encoding)) return false;
        if (!Objects.equals(listener, other.listener)) return false;
        if (!Objects.equals(host, other.host)) return false;
        return true;
    }

    /** The singleton builder. */
    public static final  Ìnstantiator<?> with = new Ìnstantiator();

    /**
     * Namespace for {@link Violet}  builder methods.
     */
    public static class Ìnstantiator<Self extends Violet & ÅssignableÅrbitrary<Self>> {

        /**
         * Create new {@link Violet} with the specified address property.
         * 
         * @return The next assignable model.
         */
        public ÅssignableTitle<Self> address(Directory address) {
            Åssignable o = new Åssignable();
            o.address(address);
            return o;
        }

        /**
         * Create new {@link Violet} with the specified address property.
         * 
         * @return The next assignable model.
         */
        public ÅssignableTitle<Self> address(String path) {
            Åssignable o = new Åssignable();
            o.address(path);
            return o;
        }

        /**
         * Create new {@link Violet} with the specified address property.
         * 
         * @return The next assignable model.
         */
        public ÅssignableTitle<Self> address(Path path) {
            Åssignable o = new Åssignable();
            o.address(path);
            return o;
        }
    }

    /**
     * Property assignment API.
     */
    public static interface ÅssignableAddress<Next> {

        /**
         * Assign address property.
         * 
         * @param value A new value to assign.
         * @return The next assignable model.
         */
        default Next address(Directory value) {
            ((Violet) this).setAddress(value);
            return (Next) this;
        }

        /**
         * Assign address property.
         * 
         * @return The next assignable model.
         */
        default Next address(String path) {
            try {
                return address((Directory) address$927011984.invoke(this, path));
            } catch (Throwable e) {
                throw quiet(e);
            }
        }

        /**
         * Assign address property.
         * 
         * @return The next assignable model.
         */
        default Next address(Path path) {
            try {
                return address((Directory) address$478361692.invoke(this, path));
            } catch (Throwable e) {
                throw quiet(e);
            }
        }
    }

    /**
     * Property assignment API.
     */
    public static interface ÅssignableTitle<Next> {

        /**
         * Assign title property.
         * 
         * @param value A new value to assign.
         * @return The next assignable model.
         */
        default Next title(String value) {
            ((Violet) this).setTitle(value);
            return (Next) this;
        }
    }

    /**
     * Property assignment API.
     */
    public static interface ÅssignableÅrbitrary<Next extends Violet> {

        /**
         * Assign sources property.
         * 
         * @param value A new value to assign.
         * @return The next assignable model.
         */
        default Next sources(List<? extends Directory> value) {
            ((Violet) this).setSources((java.util.List)value);
            return (Next) this;
        }

        /**
         * Assign sources property.
         * 
         * @return The next assignable model.
         */
        default Next sources(Directory... values) {
            return sources(List.of(values));
        }

        /**
         * Assign sources property.
         * 
         * @return The next assignable model.
         */
        default Next sources(String... paths) {
            try {
                return sources((List<Directory>) sources$640847889.invoke(this, paths));
            } catch (Throwable e) {
                throw quiet(e);
            }
        }

        /**
         * Assign sources property.
         * 
         * @return The next assignable model.
         */
        default Next sources(Path... paths) {
            try {
                return sources((List<Directory>) sources$1893898243.invoke(this, paths));
            } catch (Throwable e) {
                throw quiet(e);
            }
        }

        /**
         * Assign documents property.
         * 
         * @param value A new value to assign.
         * @return The next assignable model.
         */
        default Next documents(List<? extends Directory> value) {
            ((Violet) this).setDocuments((java.util.List)value);
            return (Next) this;
        }

        /**
         * Assign documents property.
         * 
         * @return The next assignable model.
         */
        default Next documents(Directory... values) {
            return documents(List.of(values));
        }

        /**
         * Assign documents property.
         * 
         * @return The next assignable model.
         */
        default Next documents(String path) {
            try {
                return documents((List<Directory>) documents$927011984.invoke(this, path));
            } catch (Throwable e) {
                throw quiet(e);
            }
        }

        /**
         * Assign documents property.
         * 
         * @return The next assignable model.
         */
        default Next documents(Path path) {
            try {
                return documents((List<Directory>) documents$478361692.invoke(this, path));
            } catch (Throwable e) {
                throw quiet(e);
            }
        }

        /**
         * Assign articles property.
         * 
         * @param value A new value to assign.
         * @return The next assignable model.
         */
        default Next articles(List<? extends Directory> value) {
            ((Violet) this).setArticles((java.util.List)value);
            return (Next) this;
        }

        /**
         * Assign articles property.
         * 
         * @return The next assignable model.
         */
        default Next articles(Directory... values) {
            return articles(List.of(values));
        }

        /**
         * Assign articles property.
         * 
         * @return The next assignable model.
         */
        default Next articles(String path) {
            try {
                return articles((List<Directory>) articles$927011984.invoke(this, path));
            } catch (Throwable e) {
                throw quiet(e);
            }
        }

        /**
         * Assign articles property.
         * 
         * @return The next assignable model.
         */
        default Next articles(Path path) {
            try {
                return articles((List<Directory>) articles$478361692.invoke(this, path));
            } catch (Throwable e) {
                throw quiet(e);
            }
        }

        /**
         * Assign classpath property.
         * 
         * @param value A new value to assign.
         * @return The next assignable model.
         */
        default Next classpath(List<? extends Location> value) {
            ((Violet) this).setClasspath((java.util.List)value);
            return (Next) this;
        }

        /**
         * Assign classpath property.
         * 
         * @return The next assignable model.
         */
        default Next classpath(Location... values) {
            return classpath(List.of(values));
        }

        /**
         * Assign classpath property.
         * 
         * @return The next assignable model.
         */
        default Next classpath(String... paths) {
            try {
                return classpath((List<Location>) classpath$640847889.invoke(this, paths));
            } catch (Throwable e) {
                throw quiet(e);
            }
        }

        /**
         * Assign classpath property.
         * 
         * @return The next assignable model.
         */
        default Next classpath(Path... paths) {
            try {
                return classpath((List<Location>) classpath$1893898243.invoke(this, paths));
            } catch (Throwable e) {
                throw quiet(e);
            }
        }

        /**
         * Assign classpath property.
         * 
         * @return The next assignable model.
         */
        default Next classpathBy(List<String> paths) {
            try {
                return classpath((List<Location>) classpathBy$754143386.invoke(this, paths));
            } catch (Throwable e) {
                throw quiet(e);
            }
        }

        /**
         * Assign description property.
         * 
         * @param value A new value to assign.
         * @return The next assignable model.
         */
        default Next description(String value) {
            ((Violet) this).setDescription(value);
            return (Next) this;
        }

        /**
         * Assign encoding property.
         * 
         * @param value A new value to assign.
         * @return The next assignable model.
         */
        default Next encoding(Charset value) {
            ((Violet) this).setEncoding(value);
            return (Next) this;
        }

        /**
         * Assign listener property.
         * 
         * @param value A new value to assign.
         * @return The next assignable model.
         */
        default Next listener(DiagnosticListener<? super JavaFileObject> value) {
            ((Violet) this).setListener((javax.tools.DiagnosticListener)value);
            return (Next) this;
        }

        /**
         * Assign listener property.
         * 
         * @return The next assignable model.
         */
        default Next mute() {
            try {
                return listener((DiagnosticListener<? super JavaFileObject>) mute$1.invoke(this));
            } catch (Throwable e) {
                throw quiet(e);
            }
        }

        /**
         * Assign host property.
         * 
         * @param value A new value to assign.
         * @return The next assignable model.
         */
        default Next host(Variable<? extends Hosting> value) {
            ((Violet) this).setHost((kiss.Variable)value);
            return (Next) this;
        }

        /**
         * Assign host property.
         * 
         * @return The next assignable model.
         */
        default Next host(Hosting value) {
            return host(Variable.of(value));
        }

        /**
         * Assign host property.
         * 
         * @return The next assignable model.
         */
        default Next host(String uri) {
            try {
                return host((Variable<Hosting>) host$927011984.invoke(this, uri));
            } catch (Throwable e) {
                throw quiet(e);
            }
        }
    }

    /**
     * Internal aggregated API.
     */
    protected static interface ÅssignableAll extends ÅssignableAddress, ÅssignableTitle {
    }

    /**
     * Mutable Model.
     */
    private static final class Åssignable extends Violet implements ÅssignableAll, ÅssignableÅrbitrary {
    }

    /**
     * The identifier for properties.
     */
    static final class My {
        static final String Address = "address";
        static final String Title = "title";
        static final String Sources = "sources";
        static final String Documents = "documents";
        static final String Articles = "articles";
        static final String Classpath = "classpath";
        static final String Description = "description";
        static final String Encoding = "encoding";
        static final String Listener = "listener";
        static final String Host = "host";
    }
}
