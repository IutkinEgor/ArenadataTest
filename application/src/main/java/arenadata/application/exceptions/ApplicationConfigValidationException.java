package arenadata.application.exceptions;

import arenadata.common.exceptions.ApplicationException;

public class ApplicationConfigValidationException extends ApplicationException {
    public ApplicationConfigValidationException(String message) {
        super(message);
    }
}
