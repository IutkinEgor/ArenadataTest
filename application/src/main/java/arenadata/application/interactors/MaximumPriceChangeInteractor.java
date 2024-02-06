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

public class MaximumPriceChangeInteractor extends Interactor implements MaximumPriceChangeUseCase {

    private final LoadCryptoPersistencePort loadCryptoPersistencePort;

    public MaximumPriceChangeInteractor(LoadCryptoPersistencePort loadCryptoPersistencePort) {
        this.loadCryptoPersistencePort = loadCryptoPersistencePort;
    }

    @Override
    public BaseResponse<Response> calculate() {
        try {
            List<CryptoCurrency> currencyList = loadCryptoPersistencePort.loadAll();

            String symbolWithMaxChange = "";
            double maxChangePercentage = Double.MIN_VALUE;

            for (CryptoCurrency currency : currencyList) {
                double changePercentage = calculateMaxPriceChangePercentage(currency);
                if (Math.abs(changePercentage) > Math.abs(maxChangePercentage) && changePercentage != Double.MIN_VALUE) {
                    maxChangePercentage = changePercentage;
                    symbolWithMaxChange = currency.symbol();
                }
            }

            if(maxChangePercentage == Double.MIN_VALUE) {
                throw new MaximumChangeCalculationException("Not enough historical data for comparison. Try next day.");
            }

            return onRequestSuccess(new MaximumPriceChangeUseCase.Response(symbolWithMaxChange,maxChangePercentage));
        } catch (Exception e){
            return onRequestFailure(e);
        }
    }

    private static double calculateMaxPriceChangePercentage(CryptoCurrency currency) {
        SortedSet<Quote> quoteHistory = currency.quoteHistory();

        if (quoteHistory.size() < 2) {
            // Not enough quotes for comparison
            return Double.MIN_VALUE;
        }

        // Find the last quote and the last quote of the previous day
        Quote lastQuote = quoteHistory.getLast();
        Quote previousDayLastQuote = findPreviousDayLastQuote(quoteHistory, lastQuote.date());

        if (previousDayLastQuote == null) {
            // No quote from the previous day
            return Double.MIN_VALUE;
        }

        // Calculate the price change percentage
        double priceChange = lastQuote.price() - previousDayLastQuote.price();
        double percentageChange = (priceChange / previousDayLastQuote.price()) * 100.0;

        return percentageChange;
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
