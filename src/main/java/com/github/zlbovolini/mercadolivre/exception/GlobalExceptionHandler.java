package com.github.zlbovolini.mercadolivre.exception;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final MessageSource messageSource;

    public GlobalExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class, BindException.class })
    public ResponseEntity<ApiErrorResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException e) {

        List<ObjectError> globalErrors = e.getBindingResult().getGlobalErrors();
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();

        ApiErrorResponse errorResponse = new ApiErrorResponse();

        globalErrors.forEach(globalError -> {
            errorResponse.addGlobalError(getErrorMessage(globalError));
        });

        fieldErrors.forEach(fieldError -> {
            String field = fieldError.getField();
            String message = getErrorMessage(fieldError);

            errorResponse.addFieldError(new FieldErrorInfo(field, message));
        });

        return ResponseEntity.badRequest().body(errorResponse);
    }

    private String getErrorMessage(ObjectError error) {
        return messageSource.getMessage(error, LocaleContextHolder.getLocale());
    }
}
