package by.vasyabylba.currencyrates.model.dto;

import by.vasyabylba.currencyrates.model.error.Violation;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class ValidationErrorResponse extends BasicErrorResponse {
    @JsonProperty("errors")
    private List<Violation> violations;

    public ValidationErrorResponse(int statusCode, String title, List<Violation> violations) {
        super(statusCode, title);
        this.violations = violations;
    }
}
