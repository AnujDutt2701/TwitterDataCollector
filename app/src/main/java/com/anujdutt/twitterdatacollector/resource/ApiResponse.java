package com.anujdutt.twitterdatacollector.resource;

import java.io.IOException;

import retrofit2.Response;

public class ApiResponse<T> {

    public T body;
    public int code;
    public String errorMessage;

    public ApiResponse(Response<T> response) {
        code = response.code();
        if(response.isSuccessful()) {
            body = response.body();
            errorMessage = null;
        } else {
            String message = null;
            if (response.errorBody() != null) {
                try {
                    message = response.errorBody().string();
                } catch (IOException ignored) {
                    //  Timber.e(ignored, "error while parsing response");
                }
            }
            if (message == null || message.trim().length() == 0) {
                message = response.message();
            }
            errorMessage = message;
            body = null;
        }
    }

    public ApiResponse(Throwable error) {
        code = 500;
        body = null;
        errorMessage = error.getMessage();
    }

    public  boolean isSuccessful() {
        return code >= 200 && code <300;
    }
}
