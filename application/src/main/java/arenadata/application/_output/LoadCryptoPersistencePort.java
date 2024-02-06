package arenadata.application._output;

import arenadata.common.exceptions.AdapterException;
import arenadata.domain.aggregate.CryptoCurrency;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * {@link LoadCryptoPersistencePort} is responsible for loading cryptocurrency data from a persistence storage into the application.
 *
 * @Port interface defines rules for interacting with the external world from the application core.
 * This port is supposed to be implemented by an appropriate adapter module.
 *
 */
public interface LoadCryptoPersistencePort {

    List<CryptoCurrency> loadAll() throws AdapterException;
    Optional<CryptoCurrency> loadById(String id) throws AdapterException;
    Optional<CryptoCurrency> loadBySymbol(String symbol) throws AdapterException;
}
