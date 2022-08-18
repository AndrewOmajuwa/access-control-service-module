package com.devoteam.accesscontrolservice.handler;

import com.devoteam.accesscontrolservice.exception.BadRequest;
import com.devoteam.accesscontrolservice.exception.ExceptionDetails;
import com.devoteam.accesscontrolservice.exception.ValidationExceptionDetails;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ExceptionRestControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationExceptionDetails> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {

        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        String fields = fieldErrors.stream().map(FieldError::getField).collect(Collectors.joining(", "));
        String fieldsMessage = fieldErrors.stream().map(FieldError::getDefaultMessage).collect(Collectors.joining(", "));


        return new ResponseEntity<>(
                ValidationExceptionDetails.builder()
                        .timestamp(LocalDateTime.now())
                        .title("Field Validation Error")
                        .status(HttpStatus.BAD_REQUEST.value())
                        .details(exception.getMessage())
                        .developerMessage("Please check the input fields below")
                        .fields(fields)
                        .fieldsMessage(fieldsMessage)
                        .build(), HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ExceptionDetails> dataIntegrityViolationException(DataIntegrityViolationException exception) {

        return new ResponseEntity<>(
                ExceptionDetails.builder()
                        .timestamp(LocalDateTime.now())
                        .title("Conflict")
                        .status(HttpStatus.CONFLICT.value())
                        .details(exception.getMessage())
                        .developerMessage("The values entered already exist in the current database")
                        .build(), HttpStatus.CONFLICT
        );
    }
}
