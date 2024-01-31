package arenadata.common.exceptions;

public abstract class DomainException extends RuntimeException{
    public DomainException(String message) {
        super(message);
    }

}