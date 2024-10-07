package com.yasykur.timesheet.handler;

import com.yasykur.timesheet.model.ApiResponse;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ResponseUtil {

    public static <T> ApiResponse<T> success(T data, String message, String path) {
        ApiResponse<T> response = new ApiResponse<>();

        response.setSuccess(true);
        response.setMessage(message);
        response.setData(data);
        response.setErrors(null);
        response.setErrorCode(0);
        response.setTimestamp(System.currentTimeMillis());
        response.setPath(path);

        return response;
    }

    public static <T> ApiResponse<T> error(List<String> errors, String message, Integer errorCode, String path) {
        ApiResponse<T> response = new ApiResponse<>();

        response.setSuccess(false);
        response.setMessage(message);
        response.setData(null);
        response.setErrors(errors);
        response.setErrorCode(errorCode);
        response.setTimestamp(System.currentTimeMillis());
        response.setPath(path);

        return response;
    }

    public static <T> ApiResponse<T> error(String error, String message, Integer errorCode, String path) {
        return error(Collections.singletonList(error), message, errorCode, path);
    }
}
