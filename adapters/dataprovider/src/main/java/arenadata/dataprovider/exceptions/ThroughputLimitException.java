package arenadata.dataprovider.exceptions;

import arenadata.common.exceptions.AdapterException;

public class ThroughputLimitException extends AdapterException {
    private final static String MESSAGE = "Reached request throughput limit.";

    public ThroughputLimitException() {
        super(MESSAGE);
    }

    public ThroughputLimitException(String message) {
        super(message);
    }
}
