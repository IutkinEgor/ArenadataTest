package arenadata.persistence.services;

import arenadata.application._output.LoadCryptoPersistencePort;
import arenadata.application._output.StoreCryptoPersistencePort;
import arenadata.common.exceptions.AdapterException;
import arenadata.domain.aggregate.CryptoCurrency;
import arenadata.persistence.client.PersistenceClient;
import arenadata.persistence.config.PersistenceConfig;
import arenadata.persistence.exceptions.PersistenceException;
import co.elastic.clients.elasticsearch.core.IndexRequest;

import java.lang.System.Logger;
import java.util.Collection;
import java.util.Optional;

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
            throw new PersistenceException("Exception during storing cryptocurrency. Message: " + e.getMessage());
        }
    }


}
