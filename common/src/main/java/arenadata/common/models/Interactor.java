package arenadata.common.models;

import arenadata.common.exceptions.AdapterException;
import arenadata.common.exceptions.ApplicationException;
import arenadata.common.exceptions.DomainException;

public class Interactor {

    protected <T> BaseResponse<T> onRequestSuccess() {
        return new BaseResponse<T>(null);
    }

    protected <T> BaseResponse<T> onRequestSuccess(T data) {
        return new BaseResponse<T>(data);
    }

    protected BaseResponse<String> onRequestFailure() {
        return BaseResponse.failure();
    }

    protected <T> BaseResponse<T> onRequestFailure(Exception e) {
        if (e instanceof ApplicationException) {
            return BaseResponse.failure(e.getMessage());
        }
        else if (e instanceof AdapterException) {
            return BaseResponse.failure(e.getMessage());
        }
        else if (e instanceof DomainException) {
            return BaseResponse.failure(e.getMessage());
        }
        else {
            return BaseResponse.failure();
        }
    }

}
