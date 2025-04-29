package evergarden;

import evergarden.AutoMemoriesDoll;
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
 * Generated model for {@link AutoMemoriesDollModel}.
 * 
 * @see <a href="https://github.com/teletha/icymanipulator">Icy Manipulator (Code Generator)</a>
 */
public class AutoMemoriesDoll extends AutoMemoriesDollModel {

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
            Method method = evergarden.AutoMemoriesDollModel.class.getDeclaredMethod(name, parameterTypes);
            method.setAccessible(true);
            return MethodHandles.lookup().unreflect(method);
        } catch (Throwable e) {
            throw quiet(e);
        }
    }

    /** The overload or intercept method invoker. */
    private static final MethodHandle sources$640847889= invoker("sources", String[].class);

    /** The overload or intercept method invoker. */
    private static final MethodHandle sources$1893898243= invoker("sources", Path[].class);

    /** The overload or intercept method invoker. */
    private static final MethodHandle output$927011984= invoker("output", String.class);

    /** The overload or intercept method invoker. */
    private static final MethodHandle output$478361692= invoker("output", Path.class);

    /** The overload or intercept method invoker. */
    private static final MethodHandle classpath$640847889= invoker("classpath", String[].class);

    /** The overload or intercept method invoker. */
    private static final MethodHandle classpath$1893898243= invoker("classpath", Path[].class);

    /** The overload or intercept method invoker. */
    private static final MethodHandle classpathBy$754143386= invoker("classpathBy", List.class);

    /** The overload or intercept method invoker. */
    private static final MethodHandle samples$927011984= invoker("samples", String.class);

    /** The overload or intercept method invoker. */
    private static final MethodHandle samples$478361692= invoker("samples", Path.class);

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
            Field field = AutoMemoriesDoll.class.getDeclaredField(name);
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
    private static final Field sourcesField = updater("sources");

    /** The fast final property updater. */
    private static final MethodHandle sourcesUpdater = handler(sourcesField);

    /** The final property updater. */
    private static final Field outputField = updater("output");

    /** The fast final property updater. */
    private static final MethodHandle outputUpdater = handler(outputField);

    /** The final property updater. */
    private static final Field titleField = updater("title");

    /** The fast final property updater. */
    private static final MethodHandle titleUpdater = handler(titleField);

    /** The final property updater. */
    private static final Field classpathField = updater("classpath");

    /** The fast final property updater. */
    private static final MethodHandle classpathUpdater = handler(classpathField);

    /** The final property updater. */
    private static final Field descriptionField = updater("description");

    /** The fast final property updater. */
    private static final MethodHandle descriptionUpdater = handler(descriptionField);

    /** The final property updater. */
    private static final Field samplesField = updater("samples");

    /** The fast final property updater. */
    private static final MethodHandle samplesUpdater = handler(samplesField);

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
    public final List<Directory> sources;

    /** The exposed property. */
    public final Directory output;

    /** The exposed property. */
    public final String title;

    /** The exposed property. */
    public final List<Location> classpath;

    /** The exposed property. */
    public final String description;

    /** The exposed property. */
    public final List<Directory> samples;

    /** The exposed property. */
    public final Charset encoding;

    /** The exposed property. */
    public final DiagnosticListener<? super JavaFileObject> listener;

    /** The exposed property. */
    public final Variable<Hosting> host;

    /**
     * HIDE CONSTRUCTOR
     */
    protected AutoMemoriesDoll() {
        this.sources = null;
        this.output = null;
        this.title = null;
        this.classpath = super.classpath();
        this.description = super.description();
        this.samples = super.samples();
        this.encoding = super.encoding();
        this.listener = super.listener();
        this.host = super.host();
    }

    /**
     * The list of source directories.
     *  
     *  @return
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
            throw new IllegalArgumentException("The sources property requires non-null value.");
        }
        try {
            sourcesUpdater.invoke(this, value);
        } catch (UnsupportedOperationException e) {
        } catch (Throwable e) {
            throw quiet(e);
        }
    }

    /**
     * Specify the directory where the product is output.
     *  
     *  @return
     */
    @Override
    public final Directory output() {
        return this.output;
    }

    /**
     * Provide classic getter API.
     *
     * @return A value of output property.
     */
    @SuppressWarnings("unused")
    private final Directory getOutput() {
        return this.output;
    }

    /**
     * Provide classic setter API.
     *
     * @paran value A new value of output property to assign.
     */
    private final void setOutput(Directory value) {
        try {
            outputUpdater.invoke(this, value);
        } catch (UnsupportedOperationException e) {
        } catch (Throwable e) {
            throw quiet(e);
        }
    }

    /**
     * The product name.
     *  
     *  @return
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
     * The list of source directories.
     *  
     *  @return
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
     * The product version.
     *  
     *  @return
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
     * Specify the directory of samples.
     *  
     *  @return
     */
    @Override
    public final List<Directory> samples() {
        return this.samples;
    }

    /**
     * Provide classic getter API.
     *
     * @return A value of samples property.
     */
    @SuppressWarnings("unused")
    private final List<Directory> getSamples() {
        return this.samples;
    }

    /**
     * Provide classic setter API.
     *
     * @paran value A new value of samples property to assign.
     */
    private final void setSamples(List<Directory> value) {
        if (value == null) {
            value = super.samples();
        }
        try {
            samplesUpdater.invoke(this, value);
        } catch (UnsupportedOperationException e) {
        } catch (Throwable e) {
            throw quiet(e);
        }
    }

    /**
     * Specify the source encoding.
     *  
     *  @return
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
     * Specify the task execution listener.
     *  
     *  @return
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
        StringBuilder builder = new StringBuilder("AutoMemoriesDoll [");
        builder.append("sources=").append(sources).append(", ");
        builder.append("output=").append(output).append(", ");
        builder.append("title=").append(title).append(", ");
        builder.append("classpath=").append(classpath).append(", ");
        builder.append("description=").append(description).append(", ");
        builder.append("samples=").append(samples).append(", ");
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
        return Objects.hash(sources, output, title, classpath, description, samples, encoding, listener, host);
    }

    /**
     * Returns true if the all properties are equal to each other and false otherwise. Consequently, if both properties are null, true is returned and if exactly one property is null, false is returned. Otherwise, equality is determined by using the equals method of the base model. 
     *
     * @return true if the all properties are equal to each other and false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof AutoMemoriesDoll == false) {
            return false;
        }

        AutoMemoriesDoll other = (AutoMemoriesDoll) o;
        if (!Objects.equals(sources, other.sources)) return false;
        if (!Objects.equals(output, other.output)) return false;
        if (!Objects.equals(title, other.title)) return false;
        if (!Objects.equals(classpath, other.classpath)) return false;
        if (!Objects.equals(description, other.description)) return false;
        if (!Objects.equals(samples, other.samples)) return false;
        if (!Objects.equals(encoding, other.encoding)) return false;
        if (!Objects.equals(listener, other.listener)) return false;
        if (!Objects.equals(host, other.host)) return false;
        return true;
    }

    /** The singleton builder. */
    public static final  Ìnstantiator<?> with = new Ìnstantiator();

    /**
     * Namespace for {@link AutoMemoriesDoll}  builder methods.
     */
    public static class Ìnstantiator<Self extends AutoMemoriesDoll & ÅssignableÅrbitrary<Self>> {

        /**
         * Create new {@link AutoMemoriesDoll} with the specified sources property.
         * 
         * @return The next assignable model.
         */
        public ÅssignableOutput<ÅssignableTitle<Self>> sources(List<Directory> sources) {
            Åssignable o = new Åssignable();
            o.sources(sources);
            return o;
        }

        /**
         * Create new {@link AutoMemoriesDoll} with the specified sources property.
         * 
         * @return The next assignable model.
         */
        public ÅssignableOutput<ÅssignableTitle<Self>> sources(Directory... values) {
            return sources(List.of(values));
        }

        /**
         * The list of source directories.
         *  
         *  @return
         */
        public ÅssignableOutput<ÅssignableTitle<Self>> sources(String... paths) {
            Åssignable o = new Åssignable();
            o.sources(paths);
            return o;
        }

        /**
         * The list of source directories.
         *  
         *  @return
         */
        public ÅssignableOutput<ÅssignableTitle<Self>> sources(Path... paths) {
            Åssignable o = new Åssignable();
            o.sources(paths);
            return o;
        }
    }

    /**
     * Property assignment API.
     */
    public static interface ÅssignableSources<Next> {

        /**
         * Assign sources property.
         * 
         * @param value A new value to assign.
         * @return The next assignable model.
         */
        default Next sources(List<? extends Directory> value) {
            ((AutoMemoriesDoll) this).setSources((java.util.List)value);
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
         * The list of source directories.
         *  
         *  @return
         */
        default Next sources(String... paths) {
            try {
                return sources((List<Directory>) sources$640847889.invoke(this, paths));
            } catch (Throwable e) {
                throw quiet(e);
            }
        }

        /**
         * The list of source directories.
         *  
         *  @return
         */
        default Next sources(Path... paths) {
            try {
                return sources((List<Directory>) sources$1893898243.invoke(this, paths));
            } catch (Throwable e) {
                throw quiet(e);
            }
        }
    }

    /**
     * Property assignment API.
     */
    public static interface ÅssignableOutput<Next> {

        /**
         * Assign output property.
         * 
         * @param value A new value to assign.
         * @return The next assignable model.
         */
        default Next output(Directory value) {
            ((AutoMemoriesDoll) this).setOutput(value);
            return (Next) this;
        }

        /**
         * Specify the directory where the product is output.
         *  
         *  @return
         */
        default Next output(String path) {
            try {
                return output((Directory) output$927011984.invoke(this, path));
            } catch (Throwable e) {
                throw quiet(e);
            }
        }

        /**
         * Specify the directory where the product is output.
         *  
         *  @return
         */
        default Next output(Path path) {
            try {
                return output((Directory) output$478361692.invoke(this, path));
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
            ((AutoMemoriesDoll) this).setTitle(value);
            return (Next) this;
        }
    }

    /**
     * Property assignment API.
     */
    public static interface ÅssignableÅrbitrary<Next extends AutoMemoriesDoll> {

        /**
         * Assign classpath property.
         * 
         * @param value A new value to assign.
         * @return The next assignable model.
         */
        default Next classpath(List<? extends Location> value) {
            ((AutoMemoriesDoll) this).setClasspath((java.util.List)value);
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
         * The list of source directories.
         *  
         *  @return
         */
        default Next classpath(String... paths) {
            try {
                return classpath((List<Location>) classpath$640847889.invoke(this, paths));
            } catch (Throwable e) {
                throw quiet(e);
            }
        }

        /**
         * The list of source directories.
         *  
         *  @return
         */
        default Next classpath(Path... paths) {
            try {
                return classpath((List<Location>) classpath$1893898243.invoke(this, paths));
            } catch (Throwable e) {
                throw quiet(e);
            }
        }

        /**
         * The list of source directories.
         *  
         *  @return
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
            ((AutoMemoriesDoll) this).setDescription(value);
            return (Next) this;
        }

        /**
         * Assign samples property.
         * 
         * @param value A new value to assign.
         * @return The next assignable model.
         */
        default Next samples(List<? extends Directory> value) {
            ((AutoMemoriesDoll) this).setSamples((java.util.List)value);
            return (Next) this;
        }

        /**
         * Assign samples property.
         * 
         * @return The next assignable model.
         */
        default Next samples(Directory... values) {
            return samples(List.of(values));
        }

        /**
         * Specify the directory of samples.
         *  
         *  @return
         */
        default Next samples(String path) {
            try {
                return samples((List<Directory>) samples$927011984.invoke(this, path));
            } catch (Throwable e) {
                throw quiet(e);
            }
        }

        /**
         * Specify the directory of samples.
         *  
         *  @return
         */
        default Next samples(Path path) {
            try {
                return samples((List<Directory>) samples$478361692.invoke(this, path));
            } catch (Throwable e) {
                throw quiet(e);
            }
        }

        /**
         * Assign encoding property.
         * 
         * @param value A new value to assign.
         * @return The next assignable model.
         */
        default Next encoding(Charset value) {
            ((AutoMemoriesDoll) this).setEncoding(value);
            return (Next) this;
        }

        /**
         * Assign listener property.
         * 
         * @param value A new value to assign.
         * @return The next assignable model.
         */
        default Next listener(DiagnosticListener<? super JavaFileObject> value) {
            ((AutoMemoriesDoll) this).setListener((javax.tools.DiagnosticListener)value);
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
            ((AutoMemoriesDoll) this).setHost((kiss.Variable)value);
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
    protected static interface ÅssignableAll extends ÅssignableSources, ÅssignableOutput, ÅssignableTitle {
    }

    /**
     * Mutable Model.
     */
    private static final class Åssignable extends AutoMemoriesDoll implements ÅssignableAll, ÅssignableÅrbitrary {
    }

    /**
     * The identifier for properties.
     */
    static final class My {
        static final String Sources = "sources";
        static final String Output = "output";
        static final String Title = "title";
        static final String Classpath = "classpath";
        static final String Description = "description";
        static final String Samples = "samples";
        static final String Encoding = "encoding";
        static final String Listener = "listener";
        static final String Host = "host";
    }
}
