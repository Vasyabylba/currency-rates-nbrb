package by.vasyabylba.currencyrates.service.impl;

import by.vasyabylba.currencyrates.exception.NotFoundException;
import by.vasyabylba.currencyrates.exception.ValidationException;
import by.vasyabylba.currencyrates.client.NbrbExratesClient;
import by.vasyabylba.currencyrates.model.dto.client.CurrencyNbrbResponse;
import by.vasyabylba.currencyrates.mapper.CurrencyMapper;
import by.vasyabylba.currencyrates.model.entity.Currency;
import by.vasyabylba.currencyrates.repository.CurrencyRepository;
import by.vasyabylba.currencyrates.util.CurrencyUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CurrencyServiceImplTest {
    @Mock
    private CurrencyRepository currencyRepository;
    @Mock
    private NbrbExratesClient nbrbExratesClient;
    @Mock
    private CurrencyMapper currencyMapper;
    @InjectMocks
    private CurrencyServiceImpl currencyService;

    @Test
    void updateCurrencies_Success() {
        List<CurrencyNbrbResponse> currencies = CurrencyUtil.getCurrencyNbrbResponses();
        when(nbrbExratesClient.getCurrencies()).thenReturn(currencies);

        currencyService.updateCurrencies(Set.of(431, 451, 462));

        verify(nbrbExratesClient, times(1)).getCurrencies();
        verify(currencyMapper, times(currencies.size())).toCurrency(any(CurrencyNbrbResponse.class));
        verify(currencyRepository, times(1)).saveAll(anyList());
    }

    @Test
    void getCurrencyByCodeAndDate_Success() {
        String curId = "840";
        LocalDate date = LocalDate.of(2024, 1, 1);
        int curMode = 1;
        Currency expactedCurrency = CurrencyUtil.getCurrency();
        when(currencyRepository.findByCodeAndDate(any(String.class), any(LocalDate.class)))
                .thenReturn(Optional.of(expactedCurrency));

        Currency actualCurrency = currencyService.getCurrency(curId, date, curMode);

        verify(currencyRepository, times(1))
                .findByCodeAndDate(any(String.class), any(LocalDate.class));
        assertNotNull(actualCurrency);
        assertEquals(expactedCurrency.getId(), actualCurrency.getId());
    }

    @Test
    void getCurrencyByCodeAndDate_NotFound() {
        String curId = "001";
        LocalDate date = LocalDate.of(2024, 1, 1);
        int curMode = 1;
        when(currencyRepository.findByCodeAndDate(any(String.class), any(LocalDate.class)))
                .thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () ->
                currencyService.getCurrency(curId, date, curMode));

        verify(currencyRepository, times(1))
                .findByCodeAndDate(any(String.class), any(LocalDate.class));
        assertThat(exception)
                .hasMessageContaining(
                        String.format("Currency rate with id = %s not found as of date %s", curId, date));
    }

    @Test
    void getCurrencyByAbbreviationAndDate_Success() {
        String curId = "USD";
        LocalDate date = LocalDate.of(2024, 1, 1);
        int curMode = 2;
        Currency expactedCurrency = CurrencyUtil.getCurrency();
        when(currencyRepository.findByAbbreviationAndDate(any(String.class), any(LocalDate.class)))
                .thenReturn(Optional.of(expactedCurrency));

        Currency actualCurrency = currencyService.getCurrency(curId, date, curMode);

        verify(currencyRepository, times(1))
                .findByAbbreviationAndDate(any(String.class), any(LocalDate.class));

        assertNotNull(actualCurrency);
        assertEquals(expactedCurrency.getId(), actualCurrency.getId());
    }


    @Test
    void getCurrencyByAbbreviationAndDate_NotFound() {
        String curId = "AAA";
        LocalDate date = LocalDate.of(2024, 1, 1);
        int curMode = 2;
        when(currencyRepository.findByAbbreviationAndDate(any(String.class), any(LocalDate.class)))
                .thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () ->
                currencyService.getCurrency(curId, date, curMode));

        verify(currencyRepository, times(1))
                .findByAbbreviationAndDate(any(String.class), any(LocalDate.class));
        assertThat(exception)
                .hasMessageContaining(
                        String.format("Currency rate with id = %s not found as of date %s", curId, date));
    }

    @Test
    void getCurrencyByCurIdAndDate_Success() {
        String curId = "431";
        LocalDate date = LocalDate.of(2024, 1, 1);
        int curMode = 0;
        Currency expactedCurrency = CurrencyUtil.getCurrency();
        when(currencyRepository.findByCurIdAndDate(anyInt(), any(LocalDate.class)))
                .thenReturn(Optional.of(expactedCurrency));

        Currency actualCurrency = currencyService.getCurrency(curId, date, curMode);

        verify(currencyRepository, times(1))
                .findByCurIdAndDate(anyInt(), any(LocalDate.class));
        assertNotNull(actualCurrency);
        assertEquals(expactedCurrency.getId(), actualCurrency.getId());
    }

    @Test
    void getCurrencyByCurIdAndDate_NotFound() {
        String curId = "001";
        LocalDate date = LocalDate.of(2024, 1, 1);
        int curMode = 0;
        when(currencyRepository.findByCurIdAndDate(anyInt(), any(LocalDate.class)))
                .thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () ->
                currencyService.getCurrency(curId, date, curMode));

        verify(currencyRepository, times(1))
                .findByCurIdAndDate(anyInt(), any(LocalDate.class));
        assertThat(exception)
                .hasMessageContaining(
                        String.format("Currency rate with id = %s not found as of date %s", curId, date));
    }

    @Test
    void getCurrencyByCurIdAndDate_BadRequest() {
        String curId = "AAA";
        LocalDate date = LocalDate.of(2024, 1, 1);
        int curMode = 0;

        ValidationException exception = assertThrows(ValidationException.class, () ->
                currencyService.getCurrency(curId, date, curMode));

        verify(currencyRepository, never())
                .findByCurIdAndDate(anyInt(), any(LocalDate.class));
        assertThat(exception)
                .hasMessageContaining(String.format("The value '%s' is not valid.", curId));
    }
}
