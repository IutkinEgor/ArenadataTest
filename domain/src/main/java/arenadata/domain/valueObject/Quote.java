package arenadata.domain.valueObject;

import arenadata.domain.exceptions.ValidationException;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Represents a historical price quote for a cryptocurrency at a specific date and time.
 *
 * @param date Date and time when the price quote was recorded. Example: 2024-01-30 15:05:00
 * @param price The price of the cryptocurrency in USD at the given date and time. Example: 4540.0233
 */
public record Quote(LocalDateTime date, Double price) implements Comparable<Quote>, Serializable {

    public Quote {
        validate();
    }

    /**
     * Validates the cryptocurrency fields.
     *
     * @throws ValidationException if any of the fields are invalid.
     */
    public void validate() throws ValidationException {
        validateDate(date);
        validatePrice(price);
    }

    private void validateDate(LocalDateTime date){
        if (date.isAfter(LocalDateTime.now())){
            throw new ValidationException("Historical date cannot be after the current time. Value: " + date);
        }
    }
    private void validatePrice(Double price){
        if(price < 0){
            throw new ValidationException("Historical price cannot contain a negative value. Value: " + price);
        }
    }

    @Override
    public int compareTo(Quote o) {
        return date.compareTo(o.date);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Quote quote = (Quote) o;
        return Objects.equals(date, quote.date) && Objects.equals(price, quote.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, price);
    }
}
