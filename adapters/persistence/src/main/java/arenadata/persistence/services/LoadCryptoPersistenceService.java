package arenadata.persistence.services;

import arenadata.application._output.LoadCryptoPersistencePort;
import arenadata.common.exceptions.AdapterException;
import arenadata.domain.aggregate.CryptoCurrency;
import arenadata.persistence.client.PersistenceClient;
import arenadata.persistence.config.PersistenceConfig;
import arenadata.persistence.exceptions.LoadPersistenceException;
import co.elastic.clients.elasticsearch.core.GetResponse;

import java.lang.System.Logger;
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
}
