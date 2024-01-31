package arenadata.application.interactors;

import arenadata.application._input.FetchAndStoreQuoteUseCase;
import arenadata.application._input.StartUseCase;
import arenadata.application.config.ApplicationConfig;
import arenadata.common.models.Interactor;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.lang.System.Logger;

/**
 * Implementation of the {@link StartUseCase} interface responsible for starting a scheduler task
 * that periodically calls {@link FetchAndStoreQuoteUseCase}.
 */
public class StartInteractor extends Interactor implements StartUseCase {
    private static final Logger logger = System.getLogger(StartInteractor.class.getName());
    private final ApplicationConfig config;
    private final ScheduledExecutorService scheduledExecutorService;
    private final FetchAndStoreQuoteUseCase fetchAndStoreQuoteUseCase;

    public StartInteractor(ApplicationConfig config, ScheduledExecutorService scheduledExecutorService, FetchAndStoreQuoteUseCase fetchAndStoreQuoteUseCase) {
        this.config = config;
        this.scheduledExecutorService = scheduledExecutorService;
        this.fetchAndStoreQuoteUseCase = fetchAndStoreQuoteUseCase;
    }


    /**
     * Starts the scheduler task with the configured period for fetching and storing quotes.
     */
    @Override
    public void start() {
        logger.log(Logger.Level.INFO, "Start scheduler task: {0}. Configuration: Period: {1} {2}", FetchAndStoreQuoteUseCase.class.getName(), this.config.schedulerPeriodInMilliseconds(),TimeUnit.MICROSECONDS);
        scheduledExecutorService.scheduleAtFixedRate(fetchAndStoreQuoteUseCase,0, this.config.schedulerPeriodInMilliseconds(),TimeUnit.MILLISECONDS);
    }

}
