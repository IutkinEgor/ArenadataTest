package arenadata.api.controllers;

import arenadata.application._input.MaximumPriceChangeUseCase;
import arenadata.common.models.BaseResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MaximumPriceChangeController extends Controller {

    private final MaximumPriceChangeUseCase maximumPriceChangeUseCase;

    public MaximumPriceChangeController(ObjectMapper mapper, MaximumPriceChangeUseCase maximumPriceChangeUseCase) {
        super(mapper);
        this.maximumPriceChangeUseCase = maximumPriceChangeUseCase;
    }

    @Override
    public String setPath() {
        return "/cryptocurrency/max-change";
    }

    @Override
    protected BaseResponse<?> getHandler() {
        return this.maximumPriceChangeUseCase.calculate();
    }
}
