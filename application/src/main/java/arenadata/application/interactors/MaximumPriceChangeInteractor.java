package arenadata.application.interactors;

import arenadata.application._input.MaximumPriceChangeUseCase;
import arenadata.application._output.LoadCryptoPersistencePort;
import arenadata.application._output.MaximumChangeCalculationException;
import arenadata.common.models.BaseResponse;
import arenadata.common.models.Interactor;
import arenadata.domain.aggregate.CryptoCurrency;
import arenadata.domain.valueObject.Quote;

import java.time.LocalDateTime;
import java.util.List;
import java.util.SortedSet;
/**
 * Implementation of the {@link MaximumPriceChangeUseCase} interface.
 * Extends the {@link Interactor} class for common functionality.
 */
public class MaximumPriceChangeInteractor extends Interactor implements MaximumPriceChangeUseCase {

    private final LoadCryptoPersistencePort loadCryptoPersistencePort;

    public MaximumPriceChangeInteractor(LoadCryptoPersistencePort loadCryptoPersistencePort) {
        this.loadCryptoPersistencePort = loadCryptoPersistencePort;
    }

    @Override
    public BaseResponse<Response> calculate() {
        try {
            List<CryptoCurrency> currencyList = loadCryptoPersistencePort.loadAll();
            LocalDateTime actualDate = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0);
            String symbolWithMaxChange = "";
            double maxChangePercentage = Double.MIN_VALUE;
            Quote todayLastQuote = null;
            Quote yesterdayLastQuote = null;

            for (CryptoCurrency currency : currencyList) {
                // Skip cryptocurrencies with insufficient historical data
                if (currency.quoteHistory().size() < 2) {
                    continue;
                }
                // Get the latest quote for today
                Quote today = currency.quoteHistory().getLast();
                // Find the last quote from yesterday
                Quote yesterday = findPreviousDayLastQuote(currency.quoteHistory(), today.date());
                // Skip if yesterday's data is missing or last quote is not today
                if(yesterday == null || !today.date().isAfter(actualDate)){
                    continue;
                }
                // Calculate the price change percentage between yesterday and today
                double changePercentage = calculateMaxPriceChangePercentage(today,yesterday);
                // Update the maximum change percentage if applicable
                if (Math.abs(changePercentage) > Math.abs(maxChangePercentage) && changePercentage != Double.MIN_VALUE) {
                    maxChangePercentage = changePercentage;
                    symbolWithMaxChange = currency.symbol();
                    todayLastQuote = today;
                    yesterdayLastQuote = yesterday;
                }
            }

            if(maxChangePercentage == Double.MIN_VALUE) {
                throw new MaximumChangeCalculationException("Not enough historical data for comparison. Try next day.");
            }

            return onRequestSuccess(new MaximumPriceChangeUseCase.Response(symbolWithMaxChange,maxChangePercentage,todayLastQuote,yesterdayLastQuote));
        } catch (Exception e){
            return onRequestFailure(e);
        }
    }

    private static double calculateMaxPriceChangePercentage(Quote today, Quote yesterday) {
        // Calculate the price change percentage
        double priceChange = today.price() - yesterday.price();
        return (priceChange / yesterday.price()) * 100.0;
    }

    private static Quote findPreviousDayLastQuote(SortedSet<Quote> sortedList, LocalDateTime currentDate) {
        // Get the start of the previous day
        LocalDateTime previousDayStart = currentDate.minusDays(1).withHour(0).withMinute(0).withSecond(0);

        // Get the end of the previous day
        LocalDateTime previousDayEnd = currentDate.minusDays(1).withHour(23).withMinute(59).withSecond(59);

        // Filter quotes falling within the previous day
        return sortedList.stream()
                .filter(quote -> quote.date().isAfter(previousDayStart) && quote.date().isBefore(previousDayEnd))
                .reduce((first, second) -> second) // Get the last quote of the previous day
                .orElse(null);
    }
}
