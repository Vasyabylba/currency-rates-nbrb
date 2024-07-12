package by.vasyabylba.currencyrates.externalapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * DTO for {@link by.vasyabylba.currencyrates.model.entity.Currency}
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyNbrbResponse {
    @JsonProperty("Cur_ID")
    int curId;
    @JsonProperty("Cur_ParentID")
    int curParentId;
    @JsonProperty("Cur_Code")
    String curCode;
    @JsonProperty("Cur_Abbreviation")
    String curAbbreviation;
    @JsonProperty("Cur_Name")
    String curName;
    @JsonProperty("Cur_Name_Bel")
    String curNameBel;
    @JsonProperty("Cur_Name_Eng")
    String curNameEng;
    @JsonProperty("Cur_QuotName")
    String curQuotName;
    @JsonProperty("Cur_QuotName_Bel")
    String curQuotNameBel;
    @JsonProperty("Cur_QuotName_Eng")
    String curQuotNameEng;
    @JsonProperty("Cur_NameMulti")
    String curNameMulti;
    @JsonProperty("Cur_Name_BelMulti")
    String curNameBelMulti;
    @JsonProperty("Cur_Name_EngMulti")
    String curNameEngMulti;
    @JsonProperty("Cur_Scale")
    String curScale;
    @JsonProperty("Cur_Periodicity")
    int curPeriodicity;
    @JsonProperty("Cur_DateStart")
    LocalDate curDateStart;
    @JsonProperty("Cur_DateEnd")
    LocalDate curDateEnd;
}
