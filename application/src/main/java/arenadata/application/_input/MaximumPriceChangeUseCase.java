package arenadata.application._input;

import arenadata.common.models.BaseResponse;

public interface MaximumPriceChangeUseCase {

    record Response(String symbol, Double percentage){};

    BaseResponse<Response> calculate();
}
