package com.spring.webfluxpracticedemo2.exceptionhandler;

import com.spring.webfluxpracticedemo2.dto.InputFailedValidationResponse;
import com.spring.webfluxpracticedemo2.exceptions.InputValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class InputValidationHandler {
    @ExceptionHandler(InputValidationException.class)
    public ResponseEntity<InputFailedValidationResponse> handlerException(InputValidationException e){
        InputFailedValidationResponse failedValidationResponse = new InputFailedValidationResponse();
        failedValidationResponse.setErrorCode(e.getErrorCode());
        failedValidationResponse.setInput(e.getInput());
        failedValidationResponse.setMessage(e.getMessage());
        return ResponseEntity.badRequest().body(failedValidationResponse);
    }
}
