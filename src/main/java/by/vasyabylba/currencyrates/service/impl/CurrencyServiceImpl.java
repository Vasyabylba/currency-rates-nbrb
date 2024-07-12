package by.vasyabylba.currencyrates.service.impl;

import by.vasyabylba.currencyrates.externalapi.client.NbrbExratesClient;
import by.vasyabylba.currencyrates.externalapi.dto.CurrencyNbrbResponse;
import by.vasyabylba.currencyrates.mapper.CurrencyMapper;
import by.vasyabylba.currencyrates.repository.CurrencyRepository;
import by.vasyabylba.currencyrates.service.CurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
