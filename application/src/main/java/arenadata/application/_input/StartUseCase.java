package arenadata.application._input;

/**
 * {@link StartUseCase} is an interface responsible for starting a scheduler task that will periodically call
 * {@link FetchAndStoreQuoteUseCase} with a predefined period.
 *
 * @UseCase interface defines rules for interacting with the application core logic from the outside.
 *
 */
public interface StartUseCase {
    void start();
}
