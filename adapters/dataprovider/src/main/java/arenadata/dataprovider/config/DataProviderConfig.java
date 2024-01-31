package arenadata.dataprovider.config;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class DataProviderConfig {
    private final String domain;
    private final String latestQuotePath;
    private final int requestTimeout;
    private final String apiHeader;
    private final Queue<String> keyQueue;

    public DataProviderConfig(String domain, String latestQuotePath, String apiHeader, Set<String> apiKeys, int requestTimeout){
        this.domain = domain;
        this.latestQuotePath = latestQuotePath;
        this.apiHeader = apiHeader;
        this.keyQueue = new LinkedList<>(apiKeys);
        this.requestTimeout = requestTimeout;
    }

    public String getDomain() {
        return domain;
    }

    public String getLatestQuotePath() {
        return latestQuotePath;
    }

    public String getApiHeader() {
        return apiHeader;
    }

    public String getApiKey() {
        var key = keyQueue.poll();
        keyQueue.add(key);
        return key;
    }

    public int getRequestTimeout() {
        return requestTimeout;
    }
}
