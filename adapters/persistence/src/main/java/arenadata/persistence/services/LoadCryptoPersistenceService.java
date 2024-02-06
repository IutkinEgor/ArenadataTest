package arenadata.persistence.services;

import arenadata.application._output.LoadCryptoPersistencePort;
import arenadata.common.exceptions.AdapterException;
import arenadata.domain.aggregate.CryptoCurrency;
import arenadata.persistence.client.PersistenceClient;
import arenadata.persistence.config.PersistenceConfig;
import arenadata.persistence.exceptions.LoadPersistenceException;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.CountResponse;
import co.elastic.clients.elasticsearch.core.GetResponse;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.json.JsonData;

import java.lang.System.Logger;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of {@link LoadCryptoPersistencePort} responsible for loading cryptocurrency data from elasticsearch persistent storage.
 */
public class LoadCryptoPersistenceService implements LoadCryptoPersistencePort {
    private final static Logger logger = System.getLogger(LoadCryptoPersistencePort.class.getName());
    private final PersistenceConfig config;
    private final PersistenceClient client;

    public LoadCryptoPersistenceService(PersistenceConfig config, PersistenceClient client) {
        this.config = config;
        this.client = client;
    }
    /**
     * Loads all cryptocurrency data from persistent storage.
     *
     * @return An {@link List} containing the loaded {@link CryptoCurrency}'s, or empty if not found.
     * @throws LoadPersistenceException If an error occurs during the loading process.
     */
    @Override
    public List<CryptoCurrency> loadAll() throws AdapterException {
        try {
            // Create a count request to get the total count of documents in the index
            CountResponse countResponse = client.getInstance().count(countRequest -> countRequest
                    .index(config.indexName())
            );

            Query allIdsQuery = Query.of(q -> q
                    .range(r -> r
                            .field("id")
                            .gt(JsonData.of(0))
                    )
            );

            SearchResponse<CryptoCurrency> response = this.client.getInstance().search(s -> s
                            .index(this.config.indexName())
                            .query(allIdsQuery)
                            .size((int) countResponse.count())
                            ,
                    CryptoCurrency.class
            );

            if (0 < response.hits().total().value()) {
                return response.hits().hits().stream().map(Hit::source).toList();
            } else {
                return Collections.emptyList(); // Document not found
            }
        } catch (Exception e){
            logger.log(Logger.Level.ERROR, "Error occurred while extracting all CryptoCurrency documents.");

            logger.log(Logger.Level.INFO, "Persistence client connection check.");

            client.verifyConnection();

            throw new LoadPersistenceException(e.getMessage());
        }
    }

    /**
     * Loads cryptocurrency data from persistent storage based on the provided id.
     *
     * @param id The id of the {@link CryptoCurrency} to be loaded.
     * @return An {@link Optional} containing the loaded {@link CryptoCurrency} if found, or empty if not found.
     * @throws LoadPersistenceException If an error occurs during the loading process.
     */
    @Override
    public Optional<CryptoCurrency> loadById(String id) throws AdapterException {
        try {
            GetResponse<CryptoCurrency> response = this.client.getInstance().get(g -> g
                            .index(this.config.indexName())
                            .id(id),
                    CryptoCurrency.class
            );
            return response.found() ? Optional.ofNullable(response.source()) : Optional.empty();
        } catch (Exception e){
            logger.log(Logger.Level.ERROR, "Error occurred while extracting CryptoCurrency from persistent storage. Cryptocurrency id: " + id);

            logger.log(Logger.Level.INFO, "Persistence client connection check.");

            client.verifyConnection();

            throw new LoadPersistenceException(e.getMessage());
        }
    }
    /**
     * Loads cryptocurrency data from persistent storage based on the provided symbol.
     *
     * @param symbol The id of the {@link CryptoCurrency} to be loaded.
     * @return An {@link Optional} containing the loaded {@link CryptoCurrency} if found, or empty if not found.
     * @throws LoadPersistenceException If an error occurs during the loading process.
     */
    @Override
    public Optional<CryptoCurrency> loadBySymbol(String symbol) throws AdapterException {
        try {
            SearchResponse<CryptoCurrency> response = this.client.getInstance().search(s -> s
                            .index(this.config.indexName())
                            .query(q -> q
                                    .match(t -> t
                                            .field("symbol")
                                            .query(symbol)
                                    )
                            ),
                    CryptoCurrency.class
            );

            if (0 < response.hits().total().value()) {
                return Optional.ofNullable(response.hits().hits().get(0).source());
            } else {
                return Optional.empty(); // Document not found
            }
        } catch (Exception e){
            logger.log(Logger.Level.ERROR, "Error occurred while extracting CryptoCurrency from persistent storage. Cryptocurrency symbol: " + symbol);

            logger.log(Logger.Level.INFO, "Persistence client connection check.");

            client.verifyConnection();

            throw new LoadPersistenceException(e.getMessage());
        }
    }
}
