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
    private static final MethodHandle capitalize$927011984= invoker("capitalize", String.class);

    /** The overload or intercept method invoker. */
    private static final MethodHandle classpath$640847889= invoker("classpath", String[].class);

    /** The overload or intercept method invoker. */
    private static final MethodHandle classpath$1893898243= invoker("classpath", Path[].class);

    /** The overload or intercept method invoker. */
    private static final MethodHandle sample$927011984= invoker("sample", String.class);

    /** The overload or intercept method invoker. */
    private static final MethodHandle sample$478361692= invoker("sample", Path.class);

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
    private static final Field productField = updater("product");

    /** The fast final property updater. */
    private static final MethodHandle productUpdater = handler(productField);

    /** The final property updater. */
    private static final Field projectField = updater("project");

    /** The fast final property updater. */
    private static final MethodHandle projectUpdater = handler(projectField);

    /** The final property updater. */
    private static final Field versionField = updater("version");

    /** The fast final property updater. */
    private static final MethodHandle versionUpdater = handler(versionField);

    /** The final property updater. */
    private static final Field classpathField = updater("classpath");

    /** The fast final property updater. */
    private static final MethodHandle classpathUpdater = handler(classpathField);

    /** The final property updater. */
    private static final Field descriptionField = updater("description");

    /** The fast final property updater. */
    private static final MethodHandle descriptionUpdater = handler(descriptionField);

    /** The final property updater. */
    private static final Field sampleField = updater("sample");

    /** The fast final property updater. */
    private static final MethodHandle sampleUpdater = handler(sampleField);

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
    public final String product;

    /** The exposed property. */
    public final String project;

    /** The exposed property. */
    public final String version;

    /** The exposed property. */
    public final List<Location> classpath;

    /** The exposed property. */
    public final String description;

    /** The exposed property. */
    public final List<Directory> sample;

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
        this.product = null;
        this.project = null;
        this.version = null;
        this.classpath = super.classpath();
        this.description = super.description();
        this.sample = super.sample();
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
    public final String product() {
        return this.product;
    }

    /**
     * Provide classic getter API.
     *
     * @return A value of product property.
     */
    @SuppressWarnings("unused")
    private final String getProduct() {
        return this.product;
    }

    /**
     * Provide classic setter API.
     *
     * @paran value A new value of product property to assign.
     */
    private final void setProduct(String value) {
        if (value == null) {
            throw new IllegalArgumentException("The product property requires non-null value.");
        }
        try {
            productUpdater.invoke(this, capitalize$927011984.invoke(this, value));
        } catch (UnsupportedOperationException e) {
        } catch (Throwable e) {
            throw quiet(e);
        }
    }

    /**
     * The project name.
     *  
     *  @return
     */
    @Override
    public final String project() {
        return this.project;
    }

    /**
     * Provide classic getter API.
     *
     * @return A value of project property.
     */
    @SuppressWarnings("unused")
    private final String getProject() {
        return this.project;
    }

    /**
     * Provide classic setter API.
     *
     * @paran value A new value of project property to assign.
     */
    private final void setProject(String value) {
        if (value == null) {
            throw new IllegalArgumentException("The project property requires non-null value.");
        }
        try {
            projectUpdater.invoke(this, value);
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
    public final String version() {
        return this.version;
    }

    /**
     * Provide classic getter API.
     *
     * @return A value of version property.
     */
    @SuppressWarnings("unused")
    private final String getVersion() {
        return this.version;
    }

    /**
     * Provide classic setter API.
     *
     * @paran value A new value of version property to assign.
     */
    private final void setVersion(String value) {
        if (value == null) {
            throw new IllegalArgumentException("The version property requires non-null value.");
        }
        try {
            versionUpdater.invoke(this, value);
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
    public final List<Directory> sample() {
        return this.sample;
    }

    /**
     * Provide classic getter API.
     *
     * @return A value of sample property.
     */
    @SuppressWarnings("unused")
    private final List<Directory> getSample() {
        return this.sample;
    }

    /**
     * Provide classic setter API.
     *
     * @paran value A new value of sample property to assign.
     */
    private final void setSample(List<Directory> value) {
        if (value == null) {
            value = super.sample();
        }
        try {
            sampleUpdater.invoke(this, value);
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
        builder.append("product=").append(product).append(", ");
        builder.append("project=").append(project).append(", ");
        builder.append("version=").append(version).append(", ");
        builder.append("classpath=").append(classpath).append(", ");
        builder.append("description=").append(description).append(", ");
        builder.append("sample=").append(sample).append(", ");
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
        return Objects.hash(sources, output, product, project, version, classpath, description, sample, encoding, listener, host);
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
        if (!Objects.equals(product, other.product)) return false;
        if (!Objects.equals(project, other.project)) return false;
        if (!Objects.equals(version, other.version)) return false;
        if (!Objects.equals(classpath, other.classpath)) return false;
        if (!Objects.equals(description, other.description)) return false;
        if (!Objects.equals(sample, other.sample)) return false;
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
        public ÅssignableOutput<ÅssignableProduct<ÅssignableProject<ÅssignableVersion<Self>>>> sources(List<Directory> sources) {
            Åssignable o = new Åssignable();
            o.sources(sources);
            return o;
        }

        /**
         * Create new {@link AutoMemoriesDoll} with the specified sources property.
         * 
         * @return The next assignable model.
         */
        public ÅssignableOutput<ÅssignableProduct<ÅssignableProject<ÅssignableVersion<Self>>>> sources(Directory... values) {
            return sources(List.of(values));
        }

        /**
         * The list of source directories.
         *  
         *  @return
         */
        public ÅssignableOutput<ÅssignableProduct<ÅssignableProject<ÅssignableVersion<Self>>>> sources(String... paths) {
            Åssignable o = new Åssignable();
            o.sources(paths);
            return o;
        }

        /**
         * The list of source directories.
         *  
         *  @return
         */
        public ÅssignableOutput<ÅssignableProduct<ÅssignableProject<ÅssignableVersion<Self>>>> sources(Path... paths) {
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
    public static interface ÅssignableProduct<Next> {

        /**
         * Assign product property.
         * 
         * @param value A new value to assign.
         * @return The next assignable model.
         */
        default Next product(String value) {
            ((AutoMemoriesDoll) this).setProduct(value);
            return (Next) this;
        }
    }

    /**
     * Property assignment API.
     */
    public static interface ÅssignableProject<Next> {

        /**
         * Assign project property.
         * 
         * @param value A new value to assign.
         * @return The next assignable model.
         */
        default Next project(String value) {
            ((AutoMemoriesDoll) this).setProject(value);
            return (Next) this;
        }
    }

    /**
     * Property assignment API.
     */
    public static interface ÅssignableVersion<Next> {

        /**
         * Assign version property.
         * 
         * @param value A new value to assign.
         * @return The next assignable model.
         */
        default Next version(String value) {
            ((AutoMemoriesDoll) this).setVersion(value);
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
         * Assign sample property.
         * 
         * @param value A new value to assign.
         * @return The next assignable model.
         */
        default Next sample(List<? extends Directory> value) {
            ((AutoMemoriesDoll) this).setSample((java.util.List)value);
            return (Next) this;
        }

        /**
         * Assign sample property.
         * 
         * @return The next assignable model.
         */
        default Next sample(Directory... values) {
            return sample(List.of(values));
        }

        /**
         * Specify the directory of samples.
         *  
         *  @return
         */
        default Next sample(String path) {
            try {
                return sample((List<Directory>) sample$927011984.invoke(this, path));
            } catch (Throwable e) {
                throw quiet(e);
            }
        }

        /**
         * Specify the directory of samples.
         *  
         *  @return
         */
        default Next sample(Path path) {
            try {
                return sample((List<Directory>) sample$478361692.invoke(this, path));
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
    protected static interface ÅssignableAll extends ÅssignableSources, ÅssignableOutput, ÅssignableProduct, ÅssignableProject, ÅssignableVersion {
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
        static final String Product = "product";
        static final String Project = "project";
        static final String Version = "version";
        static final String Classpath = "classpath";
        static final String Description = "description";
        static final String Sample = "sample";
        static final String Encoding = "encoding";
        static final String Listener = "listener";
        static final String Host = "host";
    }
}
