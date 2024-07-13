package by.vasyabylba.currencyrates.util;

import by.vasyabylba.currencyrates.model.dto.client.RateNbrbResponse;
import by.vasyabylba.currencyrates.model.dto.RateResponse;
import by.vasyabylba.currencyrates.model.entity.Rate;
import by.vasyabylba.currencyrates.model.entity.RateId;
import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.util.List;

@UtilityClass
public class RateUtil {
    public List<RateNbrbResponse> getRateNbrbResponses() {
        var usd = new RateNbrbResponse();
        usd.setCurId(431);
        usd.setDate(LocalDate.of(2024, 1, 1));
        usd.setCurAbbreviation("USD");
        usd.setCurScale("1");
        usd.setCurName("Доллар США");
        usd.setCurOfficialRate("3.1775");

        var eur = new RateNbrbResponse();
        usd.setCurId(451);
        usd.setDate(LocalDate.of(2024, 1, 1));
        usd.setCurAbbreviation("EUR");
        usd.setCurScale("1");
        usd.setCurName("Евро");
        usd.setCurOfficialRate("3.5363");

        var cny = new RateNbrbResponse();
        usd.setCurId(462);
        usd.setDate(LocalDate.of(2024, 1, 1));
        usd.setCurAbbreviation("CNY");
        usd.setCurScale("10");
        usd.setCurName("Китайских юаней");
        usd.setCurOfficialRate("4.4414");
        return List.of(usd, eur, cny);
    }

    public Rate getRate() {
        return new Rate(
                new RateId(LocalDate.of(2024, 1, 1), 431),
                "3.1775"
        );
    }

    public RateResponse getRateResponse() {
        var rateResponse = new RateResponse();
        rateResponse.setCurId(431);
        rateResponse.setDate(LocalDate.of(2024, 1, 1));
        rateResponse.setCurAbbreviation("USD");
        rateResponse.setCurScale("1");
        rateResponse.setCurName("Доллар США");
        rateResponse.setCurOfficialRate("3.1775");
        return rateResponse;
    }
}
