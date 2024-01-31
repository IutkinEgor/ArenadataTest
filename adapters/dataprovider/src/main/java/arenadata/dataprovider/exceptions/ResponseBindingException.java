package arenadata.dataprovider.exceptions;

import arenadata.common.exceptions.AdapterException;

public class ResponseBindingException extends AdapterException {
    private final static String MESSAGE = "Failed to bind response body to expected object due to compatibility issue.";
    public ResponseBindingException() {
        super(MESSAGE);
    }
}
