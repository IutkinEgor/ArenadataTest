package arenadata.api.server;

import arenadata.api.config.APIConfig;
import arenadata.api.controllers.Controller;
import arenadata.api.exceptions.ServerInitException;
import com.sun.net.httpserver.HttpServer;

import java.net.InetSocketAddress;
import java.util.concurrent.Executor;

import java.lang.System.Logger;

/**
 * Implementation of the {@link AppServer} interface.
 */
public class AppServerImpl implements AppServer{
    private final Logger logger = System.getLogger(AppServerImpl.class.getName());
    private final APIConfig config;
    private final HttpServer server;

    public AppServerImpl(APIConfig config, Executor executor) {
        try {
            this.config = config;
            this.server = HttpServer.create(new InetSocketAddress(config.address(),config.port()), 0);
            this.server.setExecutor(executor);
        }catch (Exception e){
            throw new ServerInitException("Error acquired during server bootstrap. Message: " + e.getMessage());
        }
    }

    @Override
    public HttpServer getInstance() {
        return this.server;
    }

    @Override
    public <C extends Controller> void registerController(C controller){
        this.logger.log(Logger.Level.INFO, "Register new controller endpoint. Path: '" + controller.setPath() + "'. Controller: " + controller.getClass().getName());
        this.server.createContext(controller.setPath(),controller);
    }

    @Override
    public void start() {
        this.server.start();
        this.logger.log(Logger.Level.INFO, "Server started at " + this.config.address() + ":" + this.config.port());
    }

    @Override
    public void stop() {
        this.server.stop(0);
        this.logger.log(Logger.Level.INFO, "Server stopped.");
    }
}
