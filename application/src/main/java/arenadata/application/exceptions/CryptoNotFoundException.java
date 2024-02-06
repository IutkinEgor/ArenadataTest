package arenadata.application.exceptions;

import arenadata.common.exceptions.ApplicationException;

public class CryptoNotFoundException extends ApplicationException {
    public CryptoNotFoundException(String message) {
        super(message);
    }
}
