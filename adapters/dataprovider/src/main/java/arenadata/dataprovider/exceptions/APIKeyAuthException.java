package arenadata.dataprovider.exceptions;

import arenadata.common.exceptions.AdapterException;

public class APIKeyAuthException extends AdapterException {
    private final static String MESSAGE = "Not Authorized access. Provided API key is not recognised by data provider.  API Key: ";
    public APIKeyAuthException(String apyKey) {
        super(MESSAGE + apyKey);
    }
}
