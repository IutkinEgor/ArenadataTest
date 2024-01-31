package arenadata.domain.aggregate;

import arenadata.domain.exceptions.ValidationException;
import arenadata.domain.valueObject.Quote;

import java.io.Serializable;
import java.util.Objects;
import java.util.SortedSet;


/**
 * Represents a cryptocurrency entity with essential information and historical quotes.
 *
 * @param id - Positive integer number uniquely identifying the cryptocurrency. Example: 1, 2, 1331
 * @param name - Worldwide-used name of the cryptocurrency. Example: Bitcoin, Ethereum
 * @param symbol - Worldwide-used symbol of the cryptocurrency. Example: BTC, ETH
 * @param quoteHistory - A sorted set of historical prices over time for the cryptocurrency.
 **/
public record CryptoCurrency(
        Integer id,
        String name,
        String symbol,
        SortedSet<Quote> quoteHistory)  implements Comparable<CryptoCurrency>, Serializable {

    public CryptoCurrency{
        validate();
    }

    /**
     * Validates the cryptocurrency fields.
     *
     * @throws ValidationException if any of the fields are invalid.
     */
    public void validate() throws ValidationException {
        validateId(id);
        validateName(name);
        validateSymbol(symbol);
        validateQuoteHistory(quoteHistory);
    }

    private void validateId(Integer id) {
        if (id <= 0) {
            throw new ValidationException("Cryptocurrency ID must be a positive integer. Value: " + id);
        }
    }

    private void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new ValidationException("Cryptocurrency name cannot be null or empty. Value: " + name);
        }
    }

    private void validateSymbol(String symbol) {
        if (symbol == null || symbol.isBlank()) {
            throw new ValidationException("Cryptocurrency symbol cannot be null or empty. Value: " + symbol);
        }
    }

    private void validateQuoteHistory(SortedSet<Quote> quoteHistory) {
        if (quoteHistory == null || quoteHistory.isEmpty()) {
            throw new ValidationException("Cryptocurrency quote history cannot be null or empty.");
        }
    }

    @Override
    public int compareTo(CryptoCurrency o) {
        return id.compareTo(o.id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CryptoCurrency currency = (CryptoCurrency) o;
        return Objects.equals(id, currency.id) && Objects.equals(name, currency.name) && Objects.equals(symbol, currency.symbol) && Objects.equals(quoteHistory, currency.quoteHistory);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, symbol, quoteHistory);
    }
}
