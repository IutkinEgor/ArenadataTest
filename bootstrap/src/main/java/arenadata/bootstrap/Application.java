package arenadata.bootstrap;

import java.util.HashMap;
import java.util.Map;
import java.lang.System.Logger;

/**
 * The {@link  Application} class represents a simple dependency injection container
 * for managing and retrieving beans in the application.
 *
 * <p>
 * It follows the Singleton pattern, ensuring that only one instance of the container exists.
 * The container holds mappings of interface classes to their corresponding object implementations.
 * </p>
 */
public class Application {

    private static final Logger logger = System.getLogger(Application.class.getName());
    // The single instance of the application container
    private static Application instance;
    // The container mapping interfaces to their implementations
    private final Map<Class, Object> container;

    private Application() {
        this.container = new HashMap<>();
    }
    /**
     * Gets the singleton instance of the application container.
     *
     * @return The singleton instance of the {@link  Application}.
     */
    public static Application getInstance() {
        if (instance == null) {
            synchronized (Application.class) {
                if (instance == null) {
                    instance = new Application();
                }
            }
        }
        return instance;
    }
    /**
     * Retrieves a bean from the container based on its interface class.
     *
     * @param <T> The type of the bean.
     * @param itf The interface class representing the type of the bean.
     * @return The bean instance associated with the provided interface class.
     */
    public <T> T getBean(Class<T> itf) {
        return itf.cast(this.container.get(itf));
    }
    /**
     * Adds a bean to the container with its corresponding interface class.
     *
     * @param <T> The type of the bean.
     * @param itf   The interface class representing the type of the bean.
     * @param obj The bean instance to be added to the container.
     */
    public <T> void addBean(Class<T> itf, T obj) {
        logger.log(Logger.Level.INFO, "Bean created: Interface - " + itf.getName() + ", Object - " + obj.getClass().getName());
        this.container.put(itf, obj);
    }
}
