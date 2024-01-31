package arenadata.persistence.exceptions;

import arenadata.common.exceptions.AdapterException;

public class LoadCryptoPersistenceException extends AdapterException {

    private final static String MESSAGE = "Error occurred while extracting CryptoCurrency from persistent storage. Cryptocurrency id: ";

    public LoadCryptoPersistenceException(String id) {
        super(MESSAGE + id);
    }
}
