package arenadata.application.interactors;

import arenadata.application._input.FetchAndStoreQuoteUseCase;
import arenadata.application._input.ManageSchedulerUseCase;
import arenadata.common.models.Interactor;

import java.lang.System.Logger;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
/**
 * Implementation of the {@link ManageSchedulerUseCase} interface responsible for managing scheduled task.
 */
public class ManageSchedulerInteractor extends Interactor implements ManageSchedulerUseCase {
    /**
     * Record to hold parameters related to scheduled tasks
     */
    private record FutureParams<T>(ScheduledFuture<?> future, T runnable, long periodInMilliseconds){};
    private final static Logger logger = System.getLogger(ManageSchedulerInteractor.class.getName());
    private final ScheduledExecutorService scheduledExecutorService;
    private final Map<Class<? extends Runnable>,FutureParams> scheduledFutureMap;

    public ManageSchedulerInteractor(ScheduledExecutorService scheduledExecutorService) {
        this.scheduledExecutorService = scheduledExecutorService;
        this.scheduledFutureMap = Collections.synchronizedMap(new HashMap<>());
    }

    @Override
    public <T extends Runnable> void schedule(Class<T> itf, T runnable, long periodTimeInMilli) {
        logger.log(Logger.Level.INFO, "Scheduler | Start new task: {0}. Configuration: Period time: {1} {2}", FetchAndStoreQuoteUseCase.class.getName(), periodTimeInMilli,TimeUnit.MICROSECONDS);
        ScheduledFuture<?> future = this.scheduledExecutorService.scheduleAtFixedRate(runnable,0,periodTimeInMilli, TimeUnit.MILLISECONDS);
        scheduledFutureMap.put(itf,new FutureParams<>(future,runnable,periodTimeInMilli));
    }

    @Override
    public <T extends Runnable> void pause(Class<T> itf, long pauseTimeInMilli) {
        // Start a new thread to handle the pause operation
        new Thread(() -> getFuture(itf).ifPresent((FutureParams<T> futureParams) ->  {
            try {
                logger.log(Logger.Level.INFO, "Scheduler | Pause task: {0}. Configuration: Pause time: {1} {2}", itf.getName(), pauseTimeInMilli,TimeUnit.MICROSECONDS);
                // Cancel the current task
                futureParams.future.cancel(true);
                // Pause for the specified duration
                Thread.sleep(pauseTimeInMilli);
                T task = itf.cast(futureParams.runnable);
                // Restart the task after pausing
                schedule(itf,task, futureParams.periodInMilliseconds);
            } catch (Exception e) {
                logger.log(Logger.Level.ERROR, "Scheduler | Failed to pause or restart task: {0}. Message: {1}", itf.getName(), e.getMessage());
            }
        })).start();
    }

    @Override
    public <T extends Runnable> void stop(Class<T> itf) {
        // Retrieve the future associated with the task and cancel it
        getFuture(itf).ifPresent((FutureParams<T> futureParams) -> {
            logger.log(Logger.Level.INFO, "Scheduler | Stop task: {0}.", itf.getName());
            futureParams.future.cancel(true);
        });
    }

    @Override
    public void shutdown() {
        // Shutdown the scheduler, stopping all scheduled tasks
        this.scheduledExecutorService.shutdown();
    }
    // Helper method to retrieve and remove the FutureParams associated with a task
    private <T extends Runnable> Optional<FutureParams<T>> getFuture(Class<T> itf) {
        return Optional.ofNullable((FutureParams<T>) this.scheduledFutureMap.remove(itf));
    }
}
