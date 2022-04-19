package com.census.support.helper.exception;


import com.example.basicecommerce.helper.response.BaseResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
@ResponseStatus
@SuppressWarnings({"unchecked","rawtypes"})
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> details = new ArrayList<>();
        BaseResponse message = new BaseResponse(false,
                ex.getMessage(),400);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(message);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> details = new ArrayList<>();
        for(ObjectError error : ex.getBindingResult().getAllErrors()) {
            details.add(error.getDefaultMessage());
        }
        BaseResponse message = new BaseResponse(false,
                "Validation failed : "+ex.getMessage(),400);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(message);
    }

    // handle SQLIntegrityConstraintViolationException exception
    @org.springframework.web.bind.annotation.ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<Object> handleSQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException ex) {
        List<String> details = new ArrayList<>();
        details.add(ex.getMessage());
        BaseResponse message = new BaseResponse(false,
                "SQLIntegrityConstraintViolationException : "+ex.getMessage(),400);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(message);
    }
}