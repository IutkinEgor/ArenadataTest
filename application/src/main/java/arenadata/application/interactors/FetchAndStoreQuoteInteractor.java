package arenadata.application.interactors;

import arenadata.application._input.FetchAndStoreQuoteUseCase;
import arenadata.application._input.ManageSchedulerUseCase;
import arenadata.application._output.LoadCryptoDataproviderPort;
import arenadata.application._output.StoreCryptoPersistencePort;
import arenadata.application.config.ApplicationConfig;
import arenadata.common.models.Interactor;
import arenadata.domain.aggregate.CryptoCurrency;

import java.lang.System.Logger;
import java.util.Collection;

/**
 * Implementation of the {@link FetchAndStoreQuoteUseCase} interface responsible for fetching
 * crypto quotes from a data provider and storing them in a persistence storage.
 * Extends the {@link Interactor} class for common functionality.
 */
public class FetchAndStoreQuoteInteractor extends Interactor implements FetchAndStoreQuoteUseCase {
    private static final Logger logger = System.getLogger(FetchAndStoreQuoteInteractor.class.getName());

    private final ApplicationConfig config;
    private final ManageSchedulerUseCase manageSchedulerUseCase;
    private final LoadCryptoDataproviderPort loadCryptoDataproviderPort;
    private final StoreCryptoPersistencePort storeCryptoPersistencePort;
    public FetchAndStoreQuoteInteractor(ApplicationConfig config, ManageSchedulerUseCase manageSchedulerUseCase, LoadCryptoDataproviderPort loadCryptoDataproviderPort, StoreCryptoPersistencePort storeCryptoPersistencePort) {
        this.config = config;
        this.manageSchedulerUseCase = manageSchedulerUseCase;
        this.loadCryptoDataproviderPort = loadCryptoDataproviderPort;
        this.storeCryptoPersistencePort = storeCryptoPersistencePort;
    }

    /**
     * Executes the logic to fetch crypto quotes from the data provider and store them in persistence storage.
     */
    @Override
    public void run() {
        try {
            logger.log(Logger.Level.INFO, "SCHEDULER TASK | Fetch crypto from data provider and store to persistence storage.");
            Collection<CryptoCurrency> cryptoCurrency = this.loadCryptoDataproviderPort.get();
            storeCryptoPersistencePort.store(cryptoCurrency);
        } catch (Exception e){
            logger.log(Logger.Level.ERROR,"SCHEDULER TASK | Error acquired while fetching data from data provider and store to persistence storage. Message: " + e.getMessage());
           // onRequestFailure(e);
            this.manageSchedulerUseCase.pause(FetchAndStoreQuoteUseCase.class, this.config.taskPauseInMilli());
        }
    }
}
