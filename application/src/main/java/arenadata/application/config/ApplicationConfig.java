package arenadata.application.config;

import arenadata.application.exceptions.ApplicationConfigValidationException;

/**
 * {@link  ApplicationConfig} defines configuration settings for the application.
 * <p>
 * Holds various configuration parameters that are used throughout the application.
 * </p>
 *
 * @param schedulerPeriodInMilliseconds Period between scheduled task in milliseconds
 */
public record ApplicationConfig(long schedulerPeriodInMilliseconds) {
    /**
     * Validates the {@link ApplicationConfig} fields.
     *
     * @throws ApplicationConfigValidationException if any of the fields are invalid.
     */
    public ApplicationConfig {
        validatePeriod(schedulerPeriodInMilliseconds);
    }

    private void validatePeriod(long schedulerPeriodInMilliseconds) throws ApplicationConfigValidationException {
        if (schedulerPeriodInMilliseconds <= 0) {
            throw new ApplicationConfigValidationException("Period between scheduled task cannot be less then or equal 0. Provided value: " + schedulerPeriodInMilliseconds);
        }
    }
}
