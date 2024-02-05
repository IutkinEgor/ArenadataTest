package arenadata.api.server;

import arenadata.api.config.APIConfig;
import arenadata.api.controllers.Controller;
import arenadata.api.exceptions.ServerInitException;
import com.sun.net.httpserver.HttpServer;

import java.net.InetSocketAddress;
import java.util.concurrent.Executor;

public class AppServerImpl implements AppServer{
    private final HttpServer server;

    public AppServerImpl(APIConfig config, Executor executor) {
        try {
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
        this.server.createContext(controller.setPath(),controller);
    }

    @Override
    public void start() {
        this.server.start();
    }

    @Override
    public void stop() {
        this.server.stop(0);

    }
}
