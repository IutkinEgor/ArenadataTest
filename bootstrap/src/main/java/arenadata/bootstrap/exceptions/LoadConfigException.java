package arenadata.bootstrap.exceptions;

import arenadata.common.exceptions.BootstrapException;
/**
 * Custom exception class for handling errors related to bootstrapping properties in to configuration classes.
 */
public class LoadConfigException extends BootstrapException {
    public LoadConfigException(String message) {
        super(message);
    }
}
