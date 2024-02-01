package arenadata.application.interactors;

import arenadata.application._input.ManageSchedulerUseCase;
import arenadata.application._input.StopUseCase;
import arenadata.common.models.Interactor;

import java.lang.System.Logger;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Implementation of the {@link StopUseCase} interface responsible for shutting down application business actions or processes.
 */
public class StopInteractor extends Interactor implements StopUseCase {

    private static final Logger logger = System.getLogger(StartInteractor.class.getName());
    private final ManageSchedulerUseCase manageSchedulerUseCase;

    public StopInteractor(ManageSchedulerUseCase manageSchedulerUseCase) {
        this.manageSchedulerUseCase = manageSchedulerUseCase;
    }

    @Override
    public void stop() {
        logger.log(Logger.Level.INFO, "Scheduler shutdown.");
        this.manageSchedulerUseCase.shutdown();
    }
}
