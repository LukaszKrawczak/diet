package com.demo.diet.exception;

import com.demo.diet.model.ApiError;
import com.demo.diet.model.ApiValidationError;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ControllerAdvice
public class MyExceptionHandler {

    @ExceptionHandler({HttpMediaTypeNotSupportedException.class})
    public ResponseEntity<Object> handleHttpMessageNotReadableException(HttpMediaTypeNotSupportedException ex) {
        ApiError apiError = new ApiError(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "Unsupported media type", ex);
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<Object> handleValidationInputData(MethodArgumentNotValidException ex) {
        ApiError error = new ApiError(HttpStatus.BAD_REQUEST, "Bad input data", ex);
        error.setSubError(getSubErrorsArray(ex));
        return buildResponseEntity(error);
    }

    @ExceptionHandler({EntityNotFoundException.class})
    public ResponseEntity<Object> handleEntityNotFound(EntityNotFoundException ex) {
        ApiError error = new ApiError(HttpStatus.NOT_FOUND, "Entity not found", ex);
        return buildResponseEntity(error);
    }

    @ExceptionHandler({EntityExistsException.class})
    public ResponseEntity<Object> handleEntityNotFound(EntityExistsException ex) {
        ApiError error = new ApiError(HttpStatus.CONFLICT, "Entity already exists", ex);
        return buildResponseEntity(error);
    }

    private List<ApiValidationError> getSubErrorsArray(MethodArgumentNotValidException ex) {
        List<ApiValidationError> subErrors = new ArrayList<>();
        ex.getFieldErrors().forEach(fieldError -> subErrors.add(new ApiValidationError(
                fieldError.getObjectName(), fieldError.getField(), fieldError.getRejectedValue(), fieldError.getDefaultMessage())
        ));
        Collections.sort(subErrors);
        return subErrors;
    }

    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
}
