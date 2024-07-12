package by.vasyabylba.currencyrates.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValidationException extends RuntimeException {
    private final String param;

    public ValidationException(String message, String param) {
        super(message);
        this.param = param;
    }
}
