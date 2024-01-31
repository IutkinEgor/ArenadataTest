package arenadata.dataprovider.exceptions;

import arenadata.common.exceptions.AdapterException;

public class TimoutException extends AdapterException {
    private final static String MESSAGE = """
            A timeout error (408) occurred while attempting to send a request to the data provider API. \
            The request has exceeded the allowed time limit. Time limit in seconds: """;
    public TimoutException(Integer timeoutInSeconds) {
        super(MESSAGE + timeoutInSeconds);
    }

    public TimoutException(String message) {
        super(message);
    }
}
