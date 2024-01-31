package arenadata.persistence.exceptions;

import arenadata.common.exceptions.AdapterException;

public class PersistenceConfigValidationException extends AdapterException {
    public PersistenceConfigValidationException(String message) {
        super(message);
    }
}
