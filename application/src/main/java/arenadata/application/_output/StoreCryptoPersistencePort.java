package arenadata.application._output;

import arenadata.common.exceptions.AdapterException;
import arenadata.domain.aggregate.CryptoCurrency;

import java.util.Collection;
/**
 * {@link StoreCryptoPersistencePort} is responsible for storing cryptocurrency data to a persistence storage.
 *
 * @Port interface defines rules for interacting with the external world from the application core.
 * This port is supposed to be implemented by an appropriate adapter module.
 *
 */
public interface StoreCryptoPersistencePort {
    void store(Collection<CryptoCurrency> currency) throws AdapterException;
}
