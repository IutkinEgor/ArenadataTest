package arenadata.dataprovider.exceptions;

import arenadata.common.exceptions.AdapterException;

public class DataProviderRequestException extends AdapterException {
    private final static String MESSAGE = "Error occurred while making a data provider API call. Returned status code: ";
    public DataProviderRequestException(Integer statusCode) {
        super(MESSAGE + statusCode);
    }
}
