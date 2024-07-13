package by.vasyabylba.currencyrates.model.error;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Violation {
    @JsonProperty("param")
    String fieldName;
    @JsonProperty("message")
    String message;
}
