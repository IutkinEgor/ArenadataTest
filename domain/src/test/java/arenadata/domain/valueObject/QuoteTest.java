package arenadata.domain.valueObject;

import arenadata.domain.exceptions.ValidationException;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class QuoteTest {

    @Test
    void createValidQuote() {
        LocalDateTime date = LocalDateTime.now().minusDays(1); // Yesterday
        Double price = 100.0;

        Quote quote = new Quote(date, price);

        assertEquals(date, quote.date());
        assertEquals(price, quote.price());
    }

    @Test
    void createQuoteWithInvalidDate() {
        LocalDateTime futureDate = LocalDateTime.now().plusDays(1);

        assertThrows(ValidationException.class, () -> new Quote(futureDate, 100.0));
    }

    @Test
    void createQuoteWithNegativePrice() {
        assertThrows(ValidationException.class, () -> new Quote(LocalDateTime.now().minusDays(1), -100.0));
    }

    @Test
    void compareToQuotes() {
        LocalDateTime date1 = LocalDateTime.now().minusDays(1);
        LocalDateTime date2 = LocalDateTime.now().minusDays(2);

        Quote quote1 = new Quote(date1, 100.0);
        Quote quote2 = new Quote(date2, 150.0);

        assertTrue(quote1.compareTo(quote2) > 0);
        assertTrue(quote2.compareTo(quote1) < 0);
        assertEquals(0, quote1.compareTo(new Quote(date1, 120.0)));
    }

    @Test
    void equalsAndHashCode() {
        LocalDateTime date = LocalDateTime.now().minusDays(1);
        Double price = 100.0;

        Quote quote1 = new Quote(date, price);
        Quote quote2 = new Quote(date, price);

        assertEquals(quote1, quote2);
        assertEquals(quote1.hashCode(), quote2.hashCode());
    }

}