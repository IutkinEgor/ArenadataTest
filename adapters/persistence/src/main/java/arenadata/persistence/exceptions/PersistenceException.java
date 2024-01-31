package arenadata.persistence.exceptions;

import arenadata.common.exceptions.AdapterException;

public class PersistenceException extends AdapterException {
    public PersistenceException(String message) {
        super(message);
    }
}
