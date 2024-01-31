package arenadata.dataprovider.services;

import arenadata.dataprovider.exceptions.ResponseBindingException;
import arenadata.domain.aggregate.CryptoCurrency;

import java.util.Collection;

public interface QuoteBinder {
    Collection<CryptoCurrency> bind(String responseBody) throws ResponseBindingException;
}
