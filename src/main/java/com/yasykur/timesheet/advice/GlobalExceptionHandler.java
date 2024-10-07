package com.yasykur.timesheet.advice;

import com.yasykur.timesheet.exception.CustomException;
import com.yasykur.timesheet.exception.UnauthorizedException;
import com.yasykur.timesheet.handler.ResponseUtil;
import com.yasykur.timesheet.model.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleException(Exception e, HttpServletRequest request) {
        ApiResponse<Void> response =
                ResponseUtil.error(Collections.singletonList(e.getMessage()), "An Error Occurred", 500,
                        request.getRequestURI());

        // return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ApiResponse<Void>> handleUnauthorizedException(UnauthorizedException e,
                                                                         HttpServletRequest request) {
        ApiResponse<Void> response =
                ResponseUtil.error(Collections.singletonList(e.getMessage()), "Unauthorized", 401,
                        request.getRequestURI());

        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ApiResponse<Void>> handleCustomException(CustomException e,
                                                                   HttpServletRequest request) {
        ApiResponse<Void> response =
                ResponseUtil.error(Collections.singletonList(e.getMessage()), e.getMessage(), e.getStatusCode().value(),
                        request.getRequestURI());

        return new ResponseEntity<>(response, e.getStatusCode());
    }
}
