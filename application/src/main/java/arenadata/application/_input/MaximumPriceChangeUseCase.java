package arenadata.application._input;

import arenadata.common.models.BaseResponse;
import arenadata.domain.valueObject.Quote;

/**
 * {@link  AveragePriceWithinAHourUseCase} is an interface responsible for determination of
 * the cryptocurrency with the maximum percentage change in price over the last day.
 *
 * @UseCase interface defines rules for interacting with the application core logic from the outside.
 *
 */
public interface MaximumPriceChangeUseCase {
    /**
     * Response body
     * @param symbol - symbol of the cryptocurrency
     * @param percentage - price change in percentage from last price record of the previous day.
     */
    record Response(String symbol, Double percentage, Quote todayLastQuote, Quote yesterdayLastQuote){};

    BaseResponse<Response> calculate();
}
