package arenadata.application._output;

import arenadata.common.exceptions.AdapterException;
import arenadata.domain.aggregate.CryptoCurrency;

import java.util.Collection;

/**
 * {@link LoadCryptoDataproviderPort} is responsible for fetching cryptocurrency data from a data provider into the application.
 *
 * @Port interface defines rules for interacting with the external world from the application core.
 * This port is supposed to be implemented by an appropriate adapter module.
 *
 */
public interface LoadCryptoDataproviderPort {
    Collection<CryptoCurrency> get() throws AdapterException;
}
