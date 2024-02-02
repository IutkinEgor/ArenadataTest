package arenadata.dataprovider.exceptions;

import arenadata.common.exceptions.AdapterException;

public class ConfigValidationException extends AdapterException {
    public ConfigValidationException(String message) {
        super(message);
    }
}
