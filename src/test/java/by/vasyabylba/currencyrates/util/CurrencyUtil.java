package by.vasyabylba.currencyrates.util;

import by.vasyabylba.currencyrates.model.dto.client.CurrencyNbrbResponse;
import by.vasyabylba.currencyrates.model.entity.Currency;
import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.util.List;

@UtilityClass
public class CurrencyUtil {
    public List<CurrencyNbrbResponse> getCurrencyNbrbResponses() {
        var usd = new CurrencyNbrbResponse();
        usd.setCurId(431);
        usd.setCurParentId(145);
        usd.setCurCode("840");
        usd.setCurAbbreviation("USD");
        usd.setCurName("Доллар США");
        usd.setCurNameBel("Долар ЗША");
        usd.setCurNameEng("US Dollar");
        usd.setCurQuotName("1 Доллар США");
        usd.setCurQuotNameBel("1 Долар ЗША");
        usd.setCurQuotNameEng("1 US Dollar");
        usd.setCurNameMulti("Долларов США");
        usd.setCurNameBelMulti("Долараў ЗША");
        usd.setCurNameEngMulti("US Dollars");
        usd.setCurScale("1");
        usd.setCurPeriodicity(0);
        usd.setCurDateStart(LocalDate.of(2021, 7, 9));
        usd.setCurDateEnd(LocalDate.of(2050, 1, 1));

        var eur = new CurrencyNbrbResponse();
        eur.setCurId(451);
        eur.setCurParentId(19);
        eur.setCurCode("978");
        eur.setCurAbbreviation("EUR");
        eur.setCurName("Евро");
        eur.setCurNameBel("Еўра");
        eur.setCurNameEng("Euro");
        eur.setCurQuotName("1 Евро");
        eur.setCurQuotNameBel("1 Еўра");
        eur.setCurQuotNameEng("1 Euro");
        eur.setCurNameMulti("Евро");
        eur.setCurNameBelMulti("Еўра");
        eur.setCurNameEngMulti("Euros");
        eur.setCurScale("1");
        eur.setCurPeriodicity(0);
        eur.setCurDateStart(LocalDate.of(2021, 7, 9));
        eur.setCurDateEnd(LocalDate.of(2050, 1, 1));

        var cny = new CurrencyNbrbResponse();
        cny.setCurId(462);
        cny.setCurParentId(28);
        cny.setCurCode("156");
        cny.setCurAbbreviation("CNY");
        cny.setCurName("Китайский юань");
        cny.setCurNameBel("Кітайскі юань");
        cny.setCurNameEng("Yuan Renminbi");
        cny.setCurQuotName("10 Китайских юаней");
        cny.setCurQuotNameBel("10 Кітайскіх юаняў");
        cny.setCurQuotNameEng("10 Yuan Renminbi");
        cny.setCurNameMulti("Китайских юаней");
        cny.setCurNameBelMulti("Кітайскіх юаняў");
        cny.setCurNameEngMulti("Yuan Renminbi");
        cny.setCurScale("10");
        cny.setCurPeriodicity(0);
        cny.setCurDateStart(LocalDate.of(2021, 7, 9));
        cny.setCurDateEnd(LocalDate.of(2050, 1, 1));
        return List.of(usd, eur, cny);
    }

    public Currency getCurrency() {
        var currency = new Currency();
        currency.setId(431);
        currency.setParentId(145);
        currency.setCode("840");
        currency.setAbbreviation("USD");
        currency.setName("Доллар США");
        currency.setNameBel("Долар ЗША");
        currency.setNameEng("US Dollar");
        currency.setQuotName("1 Доллар США");
        currency.setQuotNameBel("1 Долар ЗША");
        currency.setQuotNameEng("1 US Dollar");
        currency.setNameMulti("Долларов США");
        currency.setNameBelMulti("Долараў ЗША");
        currency.setNameEngMulti("US Dollars");
        currency.setScale("1");
        currency.setPeriodicity(0);
        currency.setDateStart(LocalDate.of(2021, 7, 9));
        currency.setDateEnd(LocalDate.of(2050, 1, 1));
        return currency;
    }
}
