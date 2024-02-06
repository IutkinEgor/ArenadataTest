package arenadata.application._input;

import arenadata.common.models.BaseResponse;
/**
 * {@link  AveragePriceWithinAHourUseCase} is an interface responsible for defining the logic
 * to calculate the average price of a cryptocurrency over the last hour.
 *
 * @UseCase interface defines rules for interacting with the application core logic from the outside.
 *
 */
public interface AveragePriceWithinAHourUseCase {
    BaseResponse<Double> calculate(String symbol);
}
