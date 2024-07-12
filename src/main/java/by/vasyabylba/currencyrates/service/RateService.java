package by.vasyabylba.currencyrates.service;

import by.vasyabylba.currencyrates.model.dto.RateResponse;
import by.vasyabylba.currencyrates.model.dto.SuccessfulResponse;

import java.time.LocalDate;

public interface RateService {
    SuccessfulResponse fetchRates(LocalDate date);

    RateResponse getRate(String curId, LocalDate date, int curMode);
}
