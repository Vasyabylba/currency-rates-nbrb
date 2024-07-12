package by.vasyabylba.currencyrates.controller;

import by.vasyabylba.currencyrates.model.dto.RateResponse;
import by.vasyabylba.currencyrates.model.dto.SuccessfulResponse;
import by.vasyabylba.currencyrates.service.RateService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/rates")
@Validated
@RequiredArgsConstructor
public class RateController {
    private static final String DEFAULT_CUR_MODE = "0";
    private final RateService rateService;

    @PostMapping("")
    public ResponseEntity<SuccessfulResponse> fetchRates(
            @RequestParam(value = "date", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate date) {
        return ResponseEntity.ok(rateService.fetchRates(date));
    }

    @GetMapping("/{curId}")
    public ResponseEntity<RateResponse> getRate(
            @PathVariable("curId")
            String curId,

            @RequestParam(name = "date", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate date,

            @RequestParam(name = "curmode", required = false, defaultValue = DEFAULT_CUR_MODE)
            @Min(value = 0, message = "must be greater than or equal to 0")
            @Max(value = 2, message = "must be less than or equal to 2")
            int curMode) {
        return ResponseEntity.ok(rateService.getRate(curId, date, curMode));
    }
}

