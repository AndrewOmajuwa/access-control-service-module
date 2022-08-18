package com.devoteam.accesscontrolservice.exception;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@SuperBuilder
public class ExceptionDetails {

    protected String title;
    protected int status;
    protected String details;
    protected LocalDateTime timestamp;
    protected String developerMessage;
}
