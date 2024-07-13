package by.vasyabylba.currencyrates.service;

import by.vasyabylba.currencyrates.model.entity.Currency;

import java.time.LocalDate;
import java.util.Set;

public interface CurrencyService {
    void updateCurrencies(Set<Integer> currencyIds);

    Currency getCurrency(String curId, LocalDate date, int curMode);
}
