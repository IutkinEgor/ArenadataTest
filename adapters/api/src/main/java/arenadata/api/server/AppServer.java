package arenadata.api.server;

import arenadata.api.controllers.Controller;
import com.sun.net.httpserver.HttpServer;
/**
 * Interface representing an application server.
 */
public interface AppServer {
    /**
     * Gets the instance of the HTTP server.
     *
     * @return The HTTP server instance.
     */
    HttpServer getInstance();
    /**
     * Registers a controller with the application server.
     *
     * @param controller The controller to register.
     * @param <C>        The type of the controller.
     */
    <C extends Controller> void registerController(C controller);
    /**
     * Start the application server.
     */
    void start();
    /**
     * Stop the application server.
     */
    void stop();
}
