package by.vasyabylba.currencyrates.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class ExplanatoryErrorResponse extends BasicErrorResponse {
    @JsonProperty("error")
    private String message;

    public ExplanatoryErrorResponse(int statusCode, String title, String message) {
        super(statusCode, title);
        this.message = message;
    }
}
