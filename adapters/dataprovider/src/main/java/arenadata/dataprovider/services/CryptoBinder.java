package arenadata.dataprovider.services;

import arenadata.dataprovider.exceptions.ResponseBindingException;
import arenadata.domain.aggregate.CryptoCurrency;

import java.util.Collection;
/**
 * Interface for binding a response body containing cryptocurrency data to a collection of CryptoCurrency objects.
 */
public interface CryptoBinder {

    /**
     * Binds the provided response body to a collection of CryptoCurrency objects.
     *
     * @param responseBody The response body containing cryptocurrency data.
     * @return A collection of CryptoCurrency objects parsed from the response body.
     * @throws ResponseBindingException If there is an issue binding the response to CryptoCurrency objects.
     */
    Collection<CryptoCurrency> bind(String responseBody) throws ResponseBindingException;
}
