package arenadata.api.controllers;

import arenadata.common.models.BaseResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * This abstract class represents a controller that handles HTTP requests.
 */
public abstract class Controller implements HttpHandler {
    /**
     * Sets the path for the controller.
     *
     * @return The path for the controller.
     */
    public abstract String setPath();
    private final ObjectMapper mapper; // ObjectMapper for JSON serialization/deserialization
    private HttpExchange exchange;

    public Controller(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        this.exchange = exchange;
        // Set response content type to JSON
        exchange.getResponseHeaders().add("Content-Type", "application/json");
        // Initialize response as a failure response
        BaseResponse<?> response = BaseResponse.failure();
        // Handle request method
        switch (exchange.getRequestMethod()) {
            case "GET"      ->  response = getHandler();
            case "POST"     ->  response = postHandler();
            case "PUT"      ->  response = putHandler();
            case "DELETE"   ->  response = deleteHandler();
        }
        // Serialize response object to JSON
        String responseBody = mapper.writeValueAsString(response);

        exchange.sendResponseHeaders(response.getCode(), responseBody.length());
        // Write response body to the output stream
        exchange.getResponseBody().write(responseBody.getBytes());
        exchange.close();
    }
    /**
     * Handles GET requests. Override to handle.
     *
     * @return The response to the GET request.
     */
    protected BaseResponse<?> getHandler(){
        return BaseResponse.failure();
    }
    /**
     * Handles POST requests. Override to handle.
     *
     * @return The response to the GET request.
     */
    protected BaseResponse<?> postHandler(){
        return BaseResponse.failure();
    }
    /**
     * Handles PUT requests. Override to handle.
     *
     * @return The response to the GET request.
     */
    protected BaseResponse<?> putHandler(){
        return BaseResponse.failure();
    }
    /**
     * Handles DELETE requests. Override to handle.
     *
     * @return The response to the GET request.
     */
    protected BaseResponse<?> deleteHandler(){
        return BaseResponse.failure();
    }
    /**
     * Gets the request parameters.
     *
     * @return A map containing the request parameters.
     */
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
    /**
     * Gets the request headers.
     *
     * @return The request headers.
     */
    public Headers getRequestHeaders(){
        return this.exchange.getRequestHeaders();
    }
    /**
     * Gets the request body.
     *
     * @param type The class type of the request body.
     * @param <T> The type of the request body.
     * @return The request body.
     * @throws RuntimeException If an error occurs during deserialization.
     * @throws IOException If an I/O error occurs.
     */
    public <T> T getRequestBody(Class<T> type) throws RuntimeException, IOException {
        return this.mapper.readValue(this.exchange.getRequestBody(), type);
    }
}
