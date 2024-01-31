package arenadata.persistence.exceptions;

import arenadata.common.exceptions.AdapterException;

public class PersistenceConnectionException extends AdapterException {
    public PersistenceConnectionException(String message) {
        super(message);
    }
}
