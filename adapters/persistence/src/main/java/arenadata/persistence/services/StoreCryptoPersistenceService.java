package arenadata.persistence.services;

import arenadata.application._output.LoadCryptoPersistencePort;
import arenadata.application._output.StoreCryptoPersistencePort;
import arenadata.common.exceptions.AdapterException;
import arenadata.domain.aggregate.CryptoCurrency;
import arenadata.persistence.client.PersistenceClient;
import arenadata.persistence.config.PersistenceConfig;
import arenadata.persistence.exceptions.StorePersistenceException;
import co.elastic.clients.elasticsearch.core.IndexRequest;

import java.lang.System.Logger;
import java.util.Collection;
import java.util.Optional;
/**
 * Implementation of {@link StoreCryptoPersistencePort} responsible for storing CryptoCurrency data in elasticsearch persistent storage.
 */
public class StoreCryptoPersistenceService implements StoreCryptoPersistencePort {
    private final static Logger logger = System.getLogger(StoreCryptoPersistencePort.class.getName());
    private final LoadCryptoPersistencePort loadCryptoPort;
    private final PersistenceConfig config;
    private final PersistenceClient client;

    public StoreCryptoPersistenceService(PersistenceConfig config, PersistenceClient client, LoadCryptoPersistencePort loadCryptoPort) {
        this.config = config;
        this.client = client;
        this.loadCryptoPort = loadCryptoPort;
    }
    /**
     * Stores a collection of CryptoCurrency objects in persistent storage.
     *
     * @param currencyCollection The collection of CryptoCurrency objects to be stored.
     * @throws StorePersistenceException If an error occurs during the storing process.
     */
    @Override
    public void store(Collection<CryptoCurrency> currencyCollection) throws AdapterException {
        try {
            for(CryptoCurrency latestCrypto : currencyCollection){

                Optional<CryptoCurrency> currentCrypto = loadCryptoPort.loadById(latestCrypto.id().toString());

                CryptoCurrency document;

                if(currentCrypto.isEmpty()){
                    logger.log(Logger.Level.INFO,"Cryptocurrency with id '" + latestCrypto.id() + "' not found. Create new one.");
                    document = latestCrypto;
                }else {
                    currentCrypto.get().quoteHistory().addAll(latestCrypto.quoteHistory());
                    document = currentCrypto.get();
                }

                IndexRequest<CryptoCurrency> request = new IndexRequest.Builder<CryptoCurrency>()
                        .index(this.config.indexName())
                        .id(latestCrypto.id().toString())
                        .document(document)
                        .build();

                this.client.getInstance().index(request);
            }
        } catch (Exception e){
            logger.log(Logger.Level.ERROR,"Error occurred while storing CryptoCurrency to persistent storage.");

            logger.log(Logger.Level.INFO, "Persistence client connection check.");

            client.verifyConnection();

            throw new StorePersistenceException(e.getMessage());
        }
    }
}
