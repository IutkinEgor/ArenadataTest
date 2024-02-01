package arenadata.persistence.services;

import arenadata.application._output.LoadCryptoPersistencePort;
import arenadata.application._output.StoreCryptoPersistencePort;
import arenadata.common.exceptions.AdapterException;
import arenadata.domain.aggregate.CryptoCurrency;
import arenadata.persistence.client.PersistenceClient;
import arenadata.persistence.config.PersistenceConfig;
import arenadata.persistence.exceptions.LoadCryptoPersistenceException;
import co.elastic.clients.elasticsearch.core.GetResponse;

import java.util.Optional;
import java.lang.System.Logger;

public class LoadCryptoPersistenceService implements LoadCryptoPersistencePort {
    private final static Logger logger = System.getLogger(LoadCryptoPersistencePort.class.getName());
    private final PersistenceConfig config;
    private final PersistenceClient client;

    public LoadCryptoPersistenceService(PersistenceConfig config, PersistenceClient client) {
        this.config = config;
        this.client = client;
    }

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
            throw new LoadCryptoPersistenceException(e.getMessage());
        }
    }
}
