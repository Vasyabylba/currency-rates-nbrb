package by.vasyabylba.currencyrates.service.impl;

import by.vasyabylba.currencyrates.exception.NotFoundException;
import by.vasyabylba.currencyrates.externalapi.client.NbrbExratesClient;
import by.vasyabylba.currencyrates.externalapi.dto.RateNbrbResponse;
import by.vasyabylba.currencyrates.mapper.RateMapper;
import by.vasyabylba.currencyrates.model.dto.SuccessfulResponse;
import by.vasyabylba.currencyrates.repository.RateRepository;
import by.vasyabylba.currencyrates.service.CurrencyService;
import by.vasyabylba.currencyrates.service.RateService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class RateServiceImpl implements RateService {
    private final NbrbExratesClient nbrbExratesClient;
    private final CurrencyService currencyService;
    private final RateRepository rateRepository;
    private final RateMapper rateMapper;

    @Override
    @Transactional
    public SuccessfulResponse fetchRates(LocalDate dateParam) {
        LocalDate date = dateParam == null ? LocalDate.now() : dateParam;
        List<RateNbrbResponse> rates = nbrbExratesClient.getRates(date);
        if (rates.isEmpty()) {
            throw new NotFoundException(String.format("Rates not found as of date %s", date));
        }
        Set<Integer> currencyIds = rates.stream().map(RateNbrbResponse::getCurId).collect(Collectors.toSet());
        currencyService.updateCurrencies(currencyIds);
        rateRepository.saveAll(rates.stream()
                .map(rateMapper::toRate)
                .toList());
        return new SuccessfulResponse("Rates have been saved in the database");
    }
}
