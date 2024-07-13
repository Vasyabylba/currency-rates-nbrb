package by.vasyabylba.currencyrates.controller;

import by.vasyabylba.currencyrates.exception.NotFoundException;
import by.vasyabylba.currencyrates.exception.ValidationException;
import by.vasyabylba.currencyrates.model.dto.ExplanatoryErrorResponse;
import by.vasyabylba.currencyrates.model.dto.ValidationErrorResponse;
import by.vasyabylba.currencyrates.model.error.Violation;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ExplanatoryErrorResponse> handleNotFoundException(NotFoundException ex) {
        var response = new ExplanatoryErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                "Not Found",
                ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ValidationErrorResponse> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        var violation = new Violation(ex.getName(), String.format("The value '%s' is not valid.", ex.getValue()));
        var response = new ValidationErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "One or more validation errors occurred.",
                List.of(violation));
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ValidationErrorResponse> handleValidationException(ValidationException ex) {
        var response = new ValidationErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "One or more validation errors occurred.",
                List.of(new Violation(ex.getParam(), ex.getMessage())));
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ValidationErrorResponse> handleConstraintValidationException(ConstraintViolationException e) {
        final List<Violation> violations = e.getConstraintViolations().stream()
                .map(violation -> {
                    String path = violation.getPropertyPath().toString();
                    String param = path.substring(path.lastIndexOf(".") + 1).toLowerCase();
                    return new Violation(param, violation.getMessage());
                })
                .collect(Collectors.toList());
        var response = new ValidationErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "One or more validation errors occurred.",
                violations);
        return ResponseEntity.badRequest().body(response);
    }
}