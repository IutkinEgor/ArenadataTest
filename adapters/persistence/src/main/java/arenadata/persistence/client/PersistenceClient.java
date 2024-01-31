package arenadata.persistence.client;

import co.elastic.clients.elasticsearch.ElasticsearchClient;

import java.io.IOException;

public interface PersistenceClient {
    ElasticsearchClient getInstance();
    void closeConnection() throws IOException;
}
