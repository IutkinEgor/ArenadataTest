package arenadata.api.server;

import arenadata.api.controllers.Controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpServer;

public interface AppServer {
    HttpServer getInstance();

    <C extends Controller> void registerController(C controller);
    void start();
    void stop();

}
