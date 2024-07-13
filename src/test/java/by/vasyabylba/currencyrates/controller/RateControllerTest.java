package by.vasyabylba.currencyrates.controller;

import by.vasyabylba.currencyrates.exception.NotFoundException;
import by.vasyabylba.currencyrates.exception.ValidationException;
import by.vasyabylba.currencyrates.model.dto.ExplanatoryErrorResponse;
import by.vasyabylba.currencyrates.model.dto.RateResponse;
import by.vasyabylba.currencyrates.model.dto.SuccessfulResponse;
import by.vasyabylba.currencyrates.model.dto.ValidationErrorResponse;
import by.vasyabylba.currencyrates.model.error.Violation;
import by.vasyabylba.currencyrates.service.RateService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RateController.class)
public class RateControllerTest {
    @MockBean
    private RateService rateService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @SneakyThrows
    @Test
    void givenNoDate_whenFetchRates_thenSuccess() {
        var successfulResponse = new SuccessfulResponse("Rates have been saved in the database");
        when(rateService.fetchRates(isNull())).thenReturn(successfulResponse);

        mockMvc.perform(post("/api/v1/rates"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(successfulResponse)));

        verify(rateService, times(1)).fetchRates(isNull());
    }

    @SneakyThrows
    @Test
    void givenDate_whenFetchRates_thenSuccess() {
        var successfulResponse = new SuccessfulResponse("Rates have been saved in the database");
        when(rateService.fetchRates(any(LocalDate.class))).thenReturn(successfulResponse);

        mockMvc.perform(post("/api/v1/rates").param("date", "2024-01-01"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(successfulResponse)));

        verify(rateService, times(1)).fetchRates(any(LocalDate.class));
    }

    @SneakyThrows
    @Test
    void fetchRates_NotFound() {
        String date = "2024-01-01";
        var notFoundException = new NotFoundException(String.format("Rates not found as of date %s", date));
        var response = new ExplanatoryErrorResponse(404, "Not Found", notFoundException.getMessage());
        when(rateService.fetchRates(any(LocalDate.class))).thenThrow(notFoundException);

        mockMvc.perform(post("/api/v1/rates").param("date", date))
                .andExpect(status().isNotFound())
                .andExpect(content().json(objectMapper.writeValueAsString(response)));

        verify(rateService, times(1)).fetchRates(any(LocalDate.class));
    }

    @SneakyThrows
    @Test
    void fetchRates_BadRequest() {
        String date = "2024.01.01";
        var response = new ValidationErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "One or more validation errors occurred.",
                List.of(new Violation(
                        "date",
                        String.format("The value '%s' is not valid.", date)
                )));

        mockMvc.perform(post("/api/v1/rates").param("date", date))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(objectMapper.writeValueAsString(response)));

        verify(rateService, never()).fetchRates(any(LocalDate.class));
    }

    @SneakyThrows
    @ParameterizedTest
    @MethodSource
    void givenAllValidParams_whenGetRate_thenSuccess(String curId, String date, String curMode) {
        RateResponse rateResponse = getRateResponse();
        when(rateService.getRate(any(String.class), any(LocalDate.class), any(Integer.class)))
                .thenReturn(rateResponse);

        mockMvc.perform(get("/api/v1/rates/{curId}", curId)
                        .param("date", date)
                        .param("curmode", curMode))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(rateResponse)));

        verify(rateService, times(1))
                .getRate(any(String.class), any(LocalDate.class), any(Integer.class));
    }

    static Stream<Arguments> givenAllValidParams_whenGetRate_thenSuccess() {
        return Stream.of(
                Arguments.of("431", "2024-01-01", "0"),
                Arguments.of("840", "2024-01-01", "1"),
                Arguments.of("USD", "2024-01-01", "2")
        );
    }

    @SneakyThrows
    @ParameterizedTest
    @MethodSource
    void givenNoDate_whenGetRate_thenSuccess(String curId, String curMode) {
        RateResponse rateResponse = getRateResponse();
        when(rateService.getRate(any(String.class), isNull(), any(Integer.class))).thenReturn(rateResponse);

        mockMvc.perform(get("/api/v1/rates/{curId}", curId)
                        .param("curmode", curMode))
                .andExpect(status().isOk())
                .andExpect(content()
                        .json(objectMapper
                                .writeValueAsString(rateResponse)));

        verify(rateService, times(1)).getRate(any(String.class), isNull(), any(Integer.class));
    }

    static Stream<Arguments> givenNoDate_whenGetRate_thenSuccess() {
        return Stream.of(
                Arguments.of("431", "0"),
                Arguments.of("840", "1"),
                Arguments.of("USD", "2")
        );
    }

    @SneakyThrows
    @Test
    void givenOnlyCurId_whenGetRate_thenSuccess() {
        RateResponse rateResponse = getRateResponse();
        when(rateService.getRate(any(String.class), isNull(), any(Integer.class))).thenReturn(rateResponse);

        mockMvc.perform(get("/api/v1/rates/{curId}", "431"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(rateResponse)));

        verify(rateService, times(1)).getRate(any(String.class), isNull(), any(Integer.class));
    }

    @SneakyThrows
    @ParameterizedTest
    @MethodSource
    void getRate_NotFound(String curId, String date, String curMode) {
        var notFoundException = new NotFoundException(
                String.format("Currency rate with id = %s not found as of date %s", curId, date));
        var response = new ExplanatoryErrorResponse(404, "Not Found", notFoundException.getMessage());
        when(rateService.getRate(any(String.class), any(LocalDate.class), any(Integer.class)))
                .thenThrow(notFoundException);

        mockMvc.perform(get("/api/v1/rates/{curId}", curId)
                        .param("date", date)
                        .param("curmode", curMode))
                .andExpect(status().isNotFound())
                .andExpect(content().json(objectMapper.writeValueAsString(response)));

        verify(rateService, times(1))
                .getRate(any(String.class), any(LocalDate.class), any(Integer.class));
    }

    static Stream<Arguments> getRate_NotFound() {
        return Stream.of(
                Arguments.of("431", "2024-01-01", "1"),
                Arguments.of("431", "2024-01-01", "2"),
                Arguments.of("840", "2024-01-01", "0"),
                Arguments.of("840", "2024-01-01", "2"),
                Arguments.of("USD", "2024-01-01", "1")
        );
    }

    @SneakyThrows
    @ParameterizedTest
    @MethodSource
    void getRate_BadRequest(String curId, String date, String curMode) {
        mockMvc.perform(get("/api/v1/rates/{curId}", curId)
                        .param("date", date)
                        .param("curmode", curMode))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status")
                        .value(400))
                .andExpect(jsonPath("$.title")
                        .value("One or more validation errors occurred."));

        verify(rateService, never()).fetchRates(any(LocalDate.class));
    }

    static Stream<Arguments> getRate_BadRequest() {
        return Stream.of(
                Arguments.of("431", "example", "0"),
                Arguments.of("431", "2024-40-40", "0"),
                Arguments.of("431", "2024-01-01", "example"),
                Arguments.of("431", "2024-01-01", "-5"),
                Arguments.of("431", "2024-01-01", "5")
        );
    }

    @SneakyThrows
    @Test
    void givenInvalidPathParam_whenGetRate_thenBadRequest() {
        String curId = "USD";
        var validationException = new ValidationException(
                String.format("The value '%s' is not valid.", curId), "curId");
        var response = new ValidationErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "One or more validation errors occurred.",
                List.of(new Violation(validationException.getParam(), validationException.getMessage())));
        when(rateService.getRate(any(String.class), any(LocalDate.class), any(Integer.class)))
                .thenThrow(validationException);

        mockMvc.perform(get("/api/v1/rates/{curId}", curId)
                        .param("date", "2024-01-01")
                        .param("curmode", "0"))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(objectMapper.writeValueAsString(response)));

        verify(rateService, times(1))
                .getRate(any(String.class), any(LocalDate.class), any(Integer.class));
    }

    private RateResponse getRateResponse() {
        var rateResponse = new RateResponse();
        rateResponse.setCurId(431);
        rateResponse.setDate(LocalDate.of(2024, 1, 1));
        rateResponse.setCurAbbreviation("USD");
        rateResponse.setCurScale("1");
        rateResponse.setCurName("Доллар США");
        rateResponse.setCurOfficialRate("3.1878");
        return rateResponse;
    }
}
