package arenadata.application.interactors;

import arenadata.application._input.FetchAndStoreQuoteUseCase;
import arenadata.application._input.ManageSchedulerUseCase;
import arenadata.application._input.StartUseCase;
import arenadata.application.config.ApplicationConfig;
import arenadata.common.models.Interactor;

import java.lang.System.Logger;
import java.util.concurrent.TimeUnit;

/**
 * Implementation of the {@link StartUseCase} interface responsible for starting a scheduler task
 * that periodically calls {@link FetchAndStoreQuoteUseCase}.
 */
public class StartInteractor extends Interactor implements StartUseCase {
    private static final Logger logger = System.getLogger(StartInteractor.class.getName());
    private final ApplicationConfig config;
    private final ManageSchedulerUseCase manageSchedulerUseCase;
    private final FetchAndStoreQuoteUseCase fetchAndStoreQuoteUseCase;

    public StartInteractor(ApplicationConfig config, ManageSchedulerUseCase manageSchedulerUseCase, FetchAndStoreQuoteUseCase fetchAndStoreQuoteUseCase) {
        this.config = config;
        this.manageSchedulerUseCase = manageSchedulerUseCase;
        this.fetchAndStoreQuoteUseCase = fetchAndStoreQuoteUseCase;
    }


    /**
     * Starts the scheduler task with the configured period for fetching and storing quotes.
     */
    @Override
    public void start() {
        logger.log(Logger.Level.INFO, "Start scheduler task: {0}. Configuration: Period: {1} {2}", FetchAndStoreQuoteUseCase.class.getName(), this.config.taskPeriodInMilli(),TimeUnit.MICROSECONDS);
        manageSchedulerUseCase.schedule(FetchAndStoreQuoteUseCase.class, fetchAndStoreQuoteUseCase, this.config.taskPeriodInMilli());
    }
}
