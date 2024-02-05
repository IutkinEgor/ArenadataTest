package arenadata.api.exceptions;

import arenadata.common.exceptions.AdapterException;

public class ServerInitException extends AdapterException {
    public ServerInitException(String message) {
        super(message);
    }
}
