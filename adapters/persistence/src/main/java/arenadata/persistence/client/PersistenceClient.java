package arenadata.persistence.client;

import arenadata.persistence.exceptions.ConnectionPersistenceException;
import co.elastic.clients.elasticsearch.ElasticsearchClient;

import java.io.IOException;
/**
 * Interface responsible for managing connections to the Elasticsearch data store.
 */
public interface PersistenceClient {
    /**
     * Get an instance of the Elasticsearch client.
     *
     * @return {@link ElasticsearchClient} client instance.
     */
    ElasticsearchClient getInstance();
    /**
     * Close the connection to the Elasticsearch data store.
     *
     * @throws IOException If an I/O error occurs during the closing process.
     */
    void closeConnection() throws IOException;
    /**
     * Verify the connection to the Elasticsearch data store.
     *
     * @throws ConnectionPersistenceException If the connection verification fails.
     */
    void verifyConnection() throws ConnectionPersistenceException;
}
