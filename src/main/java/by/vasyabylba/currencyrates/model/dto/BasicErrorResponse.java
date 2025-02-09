package by.vasyabylba.currencyrates.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BasicErrorResponse {
    @JsonProperty("status")
    private int statusCode;
    @JsonProperty("title")
    private String title;
}
