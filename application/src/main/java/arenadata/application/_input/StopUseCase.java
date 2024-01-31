package arenadata.application._input;

/**
 * {@link  StopUseCase} is an interface responsible for shutting down application business actions or processes.
 *  It is intended to be invoked before application termination.
 *
 * @UseCase interface defines rules for interacting with the application core logic from the outside.
 *
 */
public interface StopUseCase {
    void stop();
}
