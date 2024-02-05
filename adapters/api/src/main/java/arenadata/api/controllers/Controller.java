package arenadata.api.controllers;

import arenadata.common.models.BaseResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.net.URI;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public abstract class Controller implements HttpHandler {
    public abstract String setPath();
    private final ObjectMapper mapper;
    private HttpExchange exchange;

    public Controller(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        this.exchange = exchange;

        exchange.getResponseHeaders().add("Content-Type", "application/json");

        BaseResponse<?> response = BaseResponse.failure();

        switch (exchange.getRequestMethod()) {
            case "GET"      ->  response = getHandler();
            case "POST"     ->  response = postHandler();
            case "PUT"      ->  response = putHandler();
            case "DELETE"   ->  response = deleteHandler();
        }

        String responseBody = mapper.writeValueAsString(response);

        exchange.sendResponseHeaders(response.getCode(), responseBody.length());
        exchange.getResponseBody().write(responseBody.getBytes());
        exchange.close();
    }

    protected BaseResponse<?> getHandler(){
        return BaseResponse.failure();
    }
    protected BaseResponse<?> postHandler(){
        return BaseResponse.failure();
    }
    protected BaseResponse<?> putHandler(){
        return BaseResponse.failure();
    }
    protected BaseResponse<?> deleteHandler(){
        return BaseResponse.failure();
    }

    public URI getRequestURI() {
        return this.exchange.getRequestURI();
    }
    public Map<String, String> getRequestParams() {
        Map<String, String> params = new HashMap<>();
        String query = this.exchange.getRequestURI().getQuery();
        if (query != null) {
            String[] pairs = query.split("&");
            for (String pair : pairs) {
                String[] keyValue = pair.split("=");
                if (keyValue.length == 2) {
                    String key = keyValue[0];
                    String value = keyValue[1];
                    params.put(key, value);
                }
            }
        }
        return params;
    }
    public Headers getRequestHeaders(){
        return this.exchange.getRequestHeaders();
    }
    public <T> T getRequestBody(Class<T> type) throws RuntimeException, IOException {
        return this.mapper.readValue(this.exchange.getRequestBody(), type);
    }

    public ObjectMapper getMapper(){
        return this.mapper;
    }

}
