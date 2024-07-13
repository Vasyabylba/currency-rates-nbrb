package by.vasyabylba.currencyrates.service.impl;

import by.vasyabylba.currencyrates.exception.NotFoundException;
import by.vasyabylba.currencyrates.exception.ValidationException;
import by.vasyabylba.currencyrates.client.NbrbExratesClient;
import by.vasyabylba.currencyrates.model.dto.client.CurrencyNbrbResponse;
import by.vasyabylba.currencyrates.mapper.CurrencyMapper;
import by.vasyabylba.currencyrates.model.entity.Currency;
import by.vasyabylba.currencyrates.repository.CurrencyRepository;
import by.vasyabylba.currencyrates.service.CurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class CurrencyServiceImpl implements CurrencyService {
    private final NbrbExratesClient nbrbExratesClient;
    private final CurrencyRepository currencyRepository;
    private final CurrencyMapper currencyMapper;


    @Override
    public void updateCurrencies(Set<Integer> currencyIds) {
        List<CurrencyNbrbResponse> currencies = nbrbExratesClient.getCurrencies();
        currencyRepository.saveAll(currencies.stream()
                .filter(currency -> currencyIds.contains(currency.getCurId()))
                .map(currencyMapper::toCurrency)
                .toList());
    }

    @Override
    public Currency getCurrency(String curId, LocalDate date, int curMode) {
        if (curMode == 1) {
            return currencyRepository.findByCodeAndDate(curId, date)
                    .orElseThrow(() -> generateNotFoundException(curId, date));
        }
        if (curMode == 2) {
            return currencyRepository.findByAbbreviationAndDate(curId, date)
                    .orElseThrow(() -> generateNotFoundException(curId, date));
        }
        try {
            return currencyRepository.findByCurIdAndDate(Integer.parseInt(curId), date)
                    .orElseThrow(() -> generateNotFoundException(curId, date));
        } catch (NumberFormatException e) {
            throw new ValidationException(String.format("The value '%s' is not valid.", curId), "curId");
        }
    }

    private NotFoundException generateNotFoundException(String curId, LocalDate date) {
        return new NotFoundException(String.format("Currency rate with id = %s not found as of date %s", curId, date)
        );
    }
}
