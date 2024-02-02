package arenadata.persistence.exceptions;

import arenadata.common.exceptions.AdapterException;

public class ConfigValidationPersistenceException extends AdapterException {
    public ConfigValidationPersistenceException(String message) {
        super(message);
    }
}
