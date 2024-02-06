package arenadata.application._input;

import arenadata.common.models.BaseResponse;

public interface AveragePriceWithinAHourUseCase {
    BaseResponse<Double> calculate(String symbol);
}
