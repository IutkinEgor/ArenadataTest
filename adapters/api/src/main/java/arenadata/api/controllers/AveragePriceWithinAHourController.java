package arenadata.api.controllers;


import arenadata.application._input.AveragePriceWithinAHourUseCase;
import arenadata.common.models.BaseResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AveragePriceWithinAHourController extends Controller {

    private final AveragePriceWithinAHourUseCase averagePriceWithinAHourUseCase;

    public AveragePriceWithinAHourController(ObjectMapper mapper, AveragePriceWithinAHourUseCase averagePriceWithinAHourUseCase) {
        super(mapper);
        this.averagePriceWithinAHourUseCase = averagePriceWithinAHourUseCase;
    }

    @Override
    public String setPath() {
        return "/cryptocurrency/average";
    }

    @Override
    public BaseResponse<?> getHandler() {
        return this.averagePriceWithinAHourUseCase.calculate(getRequestParams().get("symbol"));
    }
}
