package arenadata.dataprovider.config;

import arenadata.dataprovider.exceptions.ConfigValidationException;

import java.net.URL;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
/**
 * {@link  DataProviderConfig} represents the configuration settings for the data provider adapter.
 *
 * @param domain Domain or URL of the data provider.
 * @param latestQuotePath Path to GET latest cryptocurrency data.
 * @param apiHeader Header name to attach API key.
 * @param apiKeys Set of API keys.
 * @param requestTimeout request timeout.
 */
public record DataProviderConfig(
        String domain,
        String latestQuotePath,
        String apiHeader,
        Queue<String> apiKeys,
        int requestTimeout
) {
    /**
     * Validates the {@link DataProviderConfig} fields.
     *
     * @throws ConfigValidationException if any of the fields are invalid.
     */
    public DataProviderConfig {
        validateDomain(domain);
        validatePath(latestQuotePath);
        validateKeys(apiKeys);
        validateRequestTimeout(requestTimeout);
    }
    private void validateDomain(String domain) throws ConfigValidationException {
        try {
            new URL(domain);
        } catch (Exception e){
            throw new ConfigValidationException("Data provider domain is not match URL pattern. Provided domain value: " + domain);
        }
    }

    private void validatePath(String path) throws ConfigValidationException {
        if (path == null || path.isBlank() || !path.startsWith("/")) {
            throw new ConfigValidationException("Data provider path cannot be null or empty. Must start with '/'. Value: " + path);
        }
    }
    private void validateKeys(Queue<String> keys) throws ConfigValidationException {
        if (keys == null || keys.size() == 0) {
            throw new ConfigValidationException("Data provider keys set cannot be empty.");
        }
    }

    private void validateRequestTimeout(int requestTimeout) throws ConfigValidationException {
        if (requestTimeout < 20) {
            throw new ConfigValidationException("Data provider request timeout cannot be lass than 20 milliseconds. Provided value: " + requestTimeout);
        }
    }
    /**
     * Retrieves the first API key from the queue and rotates it to the end of the queue.
     *
     * @return The retrieved API key.
     */
    public String getApiKey() {
        String key = apiKeys.poll();
        apiKeys.offer(key);
        return key;
    }
}

