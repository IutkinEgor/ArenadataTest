package arenadata.api.controllers;


import arenadata.common.models.BaseResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CryptocurrencyController extends Controller {

    public CryptocurrencyController(ObjectMapper mapper) {
        super(mapper);
    }

    @Override
    public String setPath() {
        return "/hello";
    }

    @Override
    public BaseResponse<?> getHandler() {
        System.out.println("Get handler/");
        return BaseResponse.success();
    }
}
