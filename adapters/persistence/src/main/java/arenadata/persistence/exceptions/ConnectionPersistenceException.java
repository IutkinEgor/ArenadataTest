package arenadata.persistence.exceptions;

import arenadata.common.exceptions.AdapterException;

public class ConnectionPersistenceException extends AdapterException {
    public ConnectionPersistenceException(String message) {
        super(message);
    }
}
