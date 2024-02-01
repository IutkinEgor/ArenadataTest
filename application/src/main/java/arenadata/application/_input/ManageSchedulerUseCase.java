package arenadata.application._input;
/**
 * {@link ManageSchedulerUseCase} is an interface responsible for managing scheduling of tasks.
 *
 * @UseCase interface defines rules for interacting with the application core logic from the outside.
 *
 */
public interface ManageSchedulerUseCase {
    /**
     * Schedule a task to run at fixed intervals.
     *
     * @param itf              The interface type representing the task.
     * @param runnable         The instance of the task to be scheduled.
     * @param periodTimeInMilli The time interval between successive executions of the task in milliseconds.
     * @param <T>              Type parameter representing a class that extends the Runnable interface.
     */
    <T extends Runnable> void schedule(Class<T> itf, T runnable, long periodTimeInMilli);
    /**
     * Pause a scheduled task for a specified duration.
     *
     * @param itf              The interface type representing the task.
     * @param pauseTimeInMilli The duration for which the task should be paused in milliseconds.
     * @param <T>              Type parameter representing a class that extends the Runnable interface.
     */
    <T extends Runnable> void pause(Class<T> itf, long pauseTimeInMilli);
    /**
     * Stop a scheduled task.
     *
     * @param itf The interface type representing the task to be stopped.
     * @param <T> Type parameter representing a class that extends the Runnable interface.
     */
    <T extends Runnable> void stop(Class<T> itf);
    /**
     * Shutdown the scheduler, stopping all scheduled tasks.
     */
    void shutdown();
}
