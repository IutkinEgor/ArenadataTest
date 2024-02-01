package arenadata.domain.aggregate;

import arenadata.domain.exceptions.ValidationException;
import arenadata.domain.valueObject.Quote;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.SortedSet;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.*;

public class CryptoCurrencyTest {
    @Test
    void createValidCryptoCurrency() {
        Integer id = 1;
        String name = "Bitcoin";
        String symbol = "BTC";
        SortedSet<Quote> quoteHistory = new TreeSet<>();
        quoteHistory.add(new Quote(LocalDateTime.now().minusDays(1), 50000.0));

        CryptoCurrency cryptoCurrency = new CryptoCurrency(id,name,symbol,quoteHistory);

        assertEquals(id, cryptoCurrency.id());
        assertEquals(name, cryptoCurrency.name());
        assertEquals(symbol, cryptoCurrency.symbol());
        assertEquals(quoteHistory, cryptoCurrency.quoteHistory());
    }

    @Test
    void createCryptoCurrencyWithInvalidId() {
        assertThrows(ValidationException.class, () ->
                new CryptoCurrency(-1, "Bitcoin", "BTC", new TreeSet<>()));
    }

    @Test
    void createCryptoCurrencyWithInvalidName() {
        assertThrows(ValidationException.class, () ->
                new CryptoCurrency(1, null, "BTC", new TreeSet<>()));
    }

    @Test
    void createCryptoCurrencyWithInvalidSymbol() {
        assertThrows(ValidationException.class, () ->
                new CryptoCurrency(1, "Bitcoin", null, new TreeSet<>()));
    }

    @Test
    void createCryptoCurrencyWithInvalidQuoteHistory() {
        assertThrows(ValidationException.class, () ->
                new CryptoCurrency(1, "Bitcoin", "BTC", null));
    }

    @Test
    void compareToCryptoCurrencies() {
        CryptoCurrency currency1 = new CryptoCurrency(1, "Bitcoin", "BTC", new TreeSet<>());
        CryptoCurrency currency2 = new CryptoCurrency(2, "Ethereum", "ETH", new TreeSet<>());

        assertTrue(currency1.compareTo(currency2) < 0);
        assertTrue(currency2.compareTo(currency1) > 0);
        assertEquals(0, currency1.compareTo(new CryptoCurrency(1, "Bitcoin", "BTC", new TreeSet<>())));

        // Ensure that comparison is based on ID
    }

    @Test
    void equalsAndHashCode() {
        CryptoCurrency currency1 = new CryptoCurrency(1, "Bitcoin", "BTC", new TreeSet<>());
        CryptoCurrency currency2 = new CryptoCurrency(1, "Bitcoin", "BTC", new TreeSet<>());

        assertEquals(currency1, currency2);
        assertEquals(currency1.hashCode(), currency2.hashCode());
    }
}
