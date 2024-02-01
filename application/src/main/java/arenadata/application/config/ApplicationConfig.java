package arenadata.application.config;

import arenadata.application.exceptions.ApplicationConfigValidationException;

/**
 * {@link  ApplicationConfig} defines configuration settings for the application.
 * <p>
 * Holds various configuration parameters that are used throughout the application.
 * </p>
 *
 * @param taskPeriodInMilli Period between task execution in milliseconds
 * @param taskPauseInMilli Pause time between task execution in milliseconds
 */
public record ApplicationConfig(long taskPeriodInMilli, long taskPauseInMilli) {
    /**
     * Validates the {@link ApplicationConfig} fields.
     *
     * @throws ApplicationConfigValidationException if any of the fields are invalid.
     */
    public ApplicationConfig {
        validate(taskPeriodInMilli);
        validate(taskPauseInMilli);
    }

    private void validate(long schedulerPeriodInMilliseconds) throws ApplicationConfigValidationException {
        if (schedulerPeriodInMilliseconds <= 0) {
            throw new ApplicationConfigValidationException("Period between scheduled task cannot be less then or equal 0. Provided value: " + schedulerPeriodInMilliseconds);
        }
    }
}
