package by.vasyabylba.currencyrates.controller;

import by.vasyabylba.currencyrates.model.dto.SuccessfulResponse;
import by.vasyabylba.currencyrates.service.RateService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/rates")
@Validated
@RequiredArgsConstructor
public class RateController {
    private final RateService rateService;

    @PostMapping("")
    public ResponseEntity<SuccessfulResponse> fetchRates(
            @RequestParam(value = "date", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate date) {
        return ResponseEntity.ok(rateService.fetchRates(date));
    }
}

