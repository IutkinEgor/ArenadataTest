package arenadata.application.interactors;

import arenadata.application._input.AveragePriceWithinAHourUseCase;
import arenadata.application._output.LoadCryptoPersistencePort;
import arenadata.application.exceptions.CryptoNotFoundException;
import arenadata.common.models.BaseResponse;
import arenadata.common.models.Interactor;
import arenadata.domain.aggregate.CryptoCurrency;
import arenadata.domain.valueObject.Quote;

import java.util.Optional;

/**
 * Implementation of the {@link AveragePriceWithinAHourUseCase} interface.
 * Extends the {@link Interactor} class for common functionality.
 */
public class AveragePriceWithinAHourInteractor extends Interactor implements AveragePriceWithinAHourUseCase {

    private final LoadCryptoPersistencePort loadCryptoPersistencePort;

    public AveragePriceWithinAHourInteractor(LoadCryptoPersistencePort loadCryptoPersistencePort) {
        this.loadCryptoPersistencePort = loadCryptoPersistencePort;
    }

    @Override
    public BaseResponse<Double> calculate(String symbol) {
        try {
            Optional<CryptoCurrency> currency = loadCryptoPersistencePort.loadBySymbol(symbol);
            if(currency.isEmpty()){
                throw new CryptoNotFoundException("Cryptocurrency with provided symbol not found. Symbol: " + symbol);
            }
            return onRequestSuccess(calculateAverage(currency.get()));
        }catch (Exception e){
            return onRequestFailure(e);
        }
    }

    private double calculateAverage(CryptoCurrency currency){
        Quote lastQuote = currency.quoteHistory().getLast();
        return currency.quoteHistory().stream()
                .filter( quote -> quote.date().isAfter(lastQuote.date().minusHours(1))).mapToDouble(Quote::price).average().orElse(0.0);
    }
}
