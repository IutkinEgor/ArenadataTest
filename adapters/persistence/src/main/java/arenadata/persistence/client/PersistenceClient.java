package arenadata.persistence.client;

import arenadata.persistence.exceptions.PersistenceConnectionException;
import co.elastic.clients.elasticsearch.ElasticsearchClient;

import java.io.IOException;

public interface PersistenceClient {
    ElasticsearchClient getInstance();
    void closeConnection() throws IOException;
    void verifyConnection() throws PersistenceConnectionException;
}
