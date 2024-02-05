package arenadata.common.models;

import java.io.Serializable;
import java.time.Instant;

public class BaseResponse<Data> implements Serializable {
        private final static String FAILURE_DEFAULT_MESSAGE = "Wrong request";
        private final Instant createdAt;
        private final Boolean isSuccess;
        private final String message;
        private final Data data;
        private final int code;
        protected BaseResponse(Boolean isSuccess, String message, Data data, int code) {
            this.createdAt = Instant.now();
            this.isSuccess = isSuccess;
            this.message = message;
            this.data = data;
            this.code = code;
        }

        public BaseResponse(Data data) {
            this(true, null, data,200);
        }
        public static BaseResponse<String> success(){
            return new BaseResponse<>(true, null, null, 200);
        }
        public static BaseResponse<String> failure(){
            return new BaseResponse<>(false, FAILURE_DEFAULT_MESSAGE, null,400);
        }
        public static BaseResponse<String> failure(int code){
            return new BaseResponse<>(false, FAILURE_DEFAULT_MESSAGE, null,code);
        }
        public static BaseResponse<String> failure(String message){
        return new BaseResponse<>(false, message, null,400);
        }
        public static BaseResponse<String> failure(String message, int code){
            return new BaseResponse<>(false, message, null,code);
        }
        public long getCreatedAt() {
            return createdAt.toEpochMilli();
        }
        public Boolean isSuccess() {
            return isSuccess;
        }
        public Boolean isFailure() {
            return !isSuccess;
        }
        public String getMessage() {
            return message;
        }
        public Data getData() {
            return data;
        }
        public int getCode() {
        return code;
    }
}
