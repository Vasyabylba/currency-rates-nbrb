package by.vasyabylba.currencyrates.service.impl;

import by.vasyabylba.currencyrates.exception.NotFoundException;
import by.vasyabylba.currencyrates.client.NbrbExratesClient;
import by.vasyabylba.currencyrates.model.dto.client.RateNbrbResponse;
import by.vasyabylba.currencyrates.mapper.RateMapper;
import by.vasyabylba.currencyrates.model.dto.RateResponse;
import by.vasyabylba.currencyrates.model.dto.SuccessfulResponse;
import by.vasyabylba.currencyrates.model.entity.Currency;
import by.vasyabylba.currencyrates.model.entity.Rate;
import by.vasyabylba.currencyrates.model.entity.RateId;
import by.vasyabylba.currencyrates.repository.RateRepository;
import by.vasyabylba.currencyrates.service.CurrencyService;
import by.vasyabylba.currencyrates.util.CurrencyUtil;
import by.vasyabylba.currencyrates.util.RateUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RateServiceImplTest {
    @Mock
    private RateRepository rateRepository;
    @Mock
    private NbrbExratesClient nbrbExratesClient;
    @Mock
    private RateMapper rateMapper;
    @Mock
    private CurrencyService currencyService;
    @InjectMocks
    private RateServiceImpl rateService;

    @Test
    void fetchRates_Success() {
        List<RateNbrbResponse> rates = RateUtil.getRateNbrbResponses();
        when(nbrbExratesClient.getRates(any(LocalDate.class))).thenReturn(rates);

        SuccessfulResponse successfulResponse = rateService.fetchRates(LocalDate.of(2024, 1, 1));

        verify(nbrbExratesClient, times(1)).getRates(any(LocalDate.class));
        verify(currencyService, times(1)).updateCurrencies(anySet());
        verify(rateRepository, times(1)).saveAll(anyList());
        verify(rateMapper, times(rates.size())).toRate(any(RateNbrbResponse.class));
        assertNotNull(successfulResponse);
        assertEquals("Rates have been saved in the database", successfulResponse.getMessage());
    }

    @Test
    void fetchRates_NotFound() {
        when(nbrbExratesClient.getRates(any(LocalDate.class))).thenReturn(Collections.emptyList());

        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> rateService.fetchRates(LocalDate.of(2024, 1, 1)));

        verify(nbrbExratesClient, times(1)).getRates(any(LocalDate.class));
        verify(currencyService, never()).updateCurrencies(anySet());
        verify(rateRepository, never()).saveAll(anyList());
        assertThat(exception).hasMessageStartingWith("Rates not found as of date");
    }

    @Test
    void getRate_Success() {
        Rate rate = RateUtil.getRate();
        Currency currency = CurrencyUtil.getCurrency();
        RateResponse rateResponse = RateUtil.getRateResponse();
        when(currencyService.getCurrency(any(String.class), any(LocalDate.class), anyInt())).thenReturn(currency);
        when(rateRepository.findById(any(RateId.class))).thenReturn(Optional.of(rate));
        when(rateMapper.toRateResponse(any(Rate.class), any(Currency.class))).thenReturn(rateResponse);

        RateResponse actualRateResponse = rateService
                .getRate("431", LocalDate.of(2024, 1, 1), 0);

        verify(currencyService, times(1))
                .getCurrency(any(String.class), any(LocalDate.class), anyInt());
        verify(rateRepository, times(1)).findById(any(RateId.class));
        verify(rateMapper, times(1)).toRateResponse(any(Rate.class), any(Currency.class));
        assertNotNull(actualRateResponse);
        assertEquals(rateResponse, actualRateResponse);
    }

    @Test
    void getRate_NotFound() {
        Currency currency = CurrencyUtil.getCurrency();
        when(currencyService.getCurrency(any(String.class), any(LocalDate.class), anyInt())).thenReturn(currency);
        when(rateRepository.findById(any(RateId.class))).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> rateService.getRate("431", LocalDate.of(2024, 1, 1), 0));

        verify(currencyService, times(1))
                .getCurrency(any(String.class), any(LocalDate.class), anyInt());
        verify(rateRepository, times(1)).findById(any(RateId.class));
        verify(rateMapper, never()).toRateResponse(any(Rate.class), any(Currency.class));
        assertThat(exception)
                .hasMessageContaining("Currency rate with id =")
                .hasMessageContaining("not found as of date");
    }
}
