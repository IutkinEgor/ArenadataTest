package arenadata.persistence.client;

import arenadata.persistence.config.PersistenceConfig;
import arenadata.persistence.exceptions.PersistenceConnectionException;
import arenadata.persistence.exceptions.PersistenceException;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.InfoResponse;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.endpoints.BooleanResponse;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;

import java.io.IOException;
import java.lang.System.Logger;

public class PersistenceClientImpl implements PersistenceClient {
    private final Logger logger = System.getLogger(PersistenceClientImpl.class.getName());
    private final PersistenceConfig config;
    private final RestClient restClient;
    private final ElasticsearchTransport elcTransport;
    private final ElasticsearchClient elcClient;

    public PersistenceClientImpl(PersistenceConfig config) {
        this.config = config;
        this.restClient = buildRestClient(this.config);
        this.elcTransport = buildElcTransport(this.restClient);
        this.elcClient = buildElcClient(this.elcTransport);
        verifyConnection();
        createIndexIfNotExist();
    }

    private RestClient buildRestClient(PersistenceConfig config){
       return RestClient.builder(HttpHost.create(config.elasticsearchDomain()))
                .build();
    }

    private ElasticsearchTransport buildElcTransport(RestClient restClient){

        ObjectMapper objectMapper = new ObjectMapper()
                .configure(SerializationFeature.INDENT_OUTPUT, false)
                .setSerializationInclusion(JsonInclude.Include.NON_NULL);

        // Serializer for LocalDateTime object.
        objectMapper.registerModule(new JavaTimeModule());

        JacksonJsonpMapper mapper = new JacksonJsonpMapper(objectMapper);

        // Create the transport with a Jackson mapper
        return new RestClientTransport(restClient, mapper);
    }

    private ElasticsearchClient buildElcClient(ElasticsearchTransport transport){
        // And create the API client
        return new ElasticsearchClient(transport);
    }

    /**
     * Verifies the connection to the Elasticsearch node using the provided ElasticsearchClient.
     * Throws PersistenceConnectionException in case of connection issues.
     *
     * @throws PersistenceConnectionException If there is an issue verifying the connection.
     */
    public void verifyConnection() throws PersistenceConnectionException {
        try {
            InfoResponse infoResponse = this.elcClient.info();
            logger.log(System.Logger.Level.INFO, "Elasticsearch cluster connection verified. Cluster info: " + infoResponse.toString());
        } catch (Exception e) {
            logger.log(System.Logger.Level.ERROR, "Error occurred during retrieval of cluster information. " +
                    "Verify connection settings and node accessibility");
            throw new PersistenceConnectionException(e.getMessage());
        }
    }

    private void createIndexIfNotExist(){
        try {
            BooleanResponse isExist = this.elcClient.indices().exists(c -> c.index(this.config.indexName()));

            if(!isExist.value()){
                this.elcClient.indices().create(c -> c.index(this.config.indexName()));
            }
        } catch (Exception e){
            throw new PersistenceException("Exception during creating index with given name. Name: " + config.indexName() +". Message: " + e.getMessage());
        }
    }

    @Override
    public ElasticsearchClient getInstance() {
        return this.elcClient;
    }

    @Override
    public void closeConnection() throws IOException {
       elcTransport.close();
    }
}
