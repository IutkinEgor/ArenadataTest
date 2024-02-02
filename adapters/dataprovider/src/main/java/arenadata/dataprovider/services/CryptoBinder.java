package arenadata.dataprovider.services;

import arenadata.dataprovider.exceptions.ResponseBindingException;
import arenadata.domain.aggregate.CryptoCurrency;

import java.util.Collection;

public interface CryptoBinder {
    Collection<CryptoCurrency> bind(String responseBody) throws ResponseBindingException;
}
