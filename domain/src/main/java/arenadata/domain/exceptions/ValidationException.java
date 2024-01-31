package arenadata.domain.exceptions;

import arenadata.common.exceptions.DomainException;

/**
 * Exception thrown to indicate a validation error in the domain model construction.
 */
public class ValidationException extends DomainException {
    public ValidationException(String message) {
        super(message);
    }
}
