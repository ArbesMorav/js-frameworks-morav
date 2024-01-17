package cz.eg.hr.controller;

import cz.eg.hr.rest.Errors;
import cz.eg.hr.rest.ValidationError;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.method.ParameterValidationResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.*;

@EnableWebMvc
@ControllerAdvice
public class GeneralControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Errors> handleValidationException(MethodArgumentNotValidException exception) {
        BindingResult result = exception.getBindingResult();
        List<ValidationError> errorList = result.getFieldErrors().stream()
            .map(e -> new ValidationError(e.getField(), e.getCode()))
            .toList();
        return ResponseEntity.badRequest().body(new Errors(errorList));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Errors> handleConstraintViolationException(ConstraintViolationException exception) {
        Set<ConstraintViolation<?>> violations = exception.getConstraintViolations();
        List<ValidationError> errorList = violations.stream()
            .map(e -> new ValidationError("Invalid field: " + e.getPropertyPath().toString(),
                "Reason: " + e.getMessage())).toList();
        return ResponseEntity.badRequest().body(new Errors(errorList));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Errors> handleArgumentTypeMismatchException(MethodArgumentTypeMismatchException exception) {
        ValidationError error = new ValidationError(exception.getErrorCode(),exception.getMessage());
        return ResponseEntity.badRequest().body(new Errors(Collections.singletonList(error)));
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<Errors> handleValidationException(HandlerMethodValidationException exception) {
        ParameterValidationResult result = exception.getAllValidationResults().get(0);
        List<ValidationError> errorList = result.getResolvableErrors().stream()
            .map(e -> new ValidationError(e.getDefaultMessage(), e.toString())).toList();
        return ResponseEntity.badRequest().body(new Errors(errorList));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Errors> handleMessageNotReadableException(HttpMessageNotReadableException exception) {
        ValidationError error = new ValidationError(exception.getMessage(),exception.getCause().toString());
        return ResponseEntity.badRequest().body(new Errors(Collections.singletonList(error)));
    }
}
