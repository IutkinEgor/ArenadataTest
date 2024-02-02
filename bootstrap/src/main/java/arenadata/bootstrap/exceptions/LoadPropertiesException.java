package arenadata.bootstrap.exceptions;

import arenadata.common.exceptions.BootstrapException;
/**
 * Custom exception class for handling errors related to loading properties in the application.
 * Extends BootstrapException to maintain consistency with the exception hierarchy.
 */
public class LoadPropertiesException extends BootstrapException {
    public LoadPropertiesException(String message) {
        super(message);
    }
}
