package by.vasyabylba.currencyrates.externalapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * DTO for {@link by.vasyabylba.currencyrates.model.entity.Rate}
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RateNbrbResponse {
    @JsonProperty("Cur_ID")
    int curId;
    @JsonProperty("Date")
    LocalDate date;
    @JsonProperty("Cur_Abbreviation")
    String curAbbreviation;
    @JsonProperty("Cur_Scale")
    String curScale;
    @JsonProperty("Cur_Name")
    String curName;
    @JsonProperty("Cur_OfficialRate")
    String curOfficialRate;
}
