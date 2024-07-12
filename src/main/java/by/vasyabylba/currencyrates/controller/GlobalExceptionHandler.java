package by.vasyabylba.currencyrates.controller;

import by.vasyabylba.currencyrates.exception.NotFoundException;
import by.vasyabylba.currencyrates.model.dto.ExplanatoryErrorResponse;
import by.vasyabylba.currencyrates.model.dto.ValidationErrorResponse;
import by.vasyabylba.currencyrates.model.error.Violation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;

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
}