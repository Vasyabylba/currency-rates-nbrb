package by.vasyabylba.currencyrates.client;

import by.vasyabylba.currencyrates.model.dto.client.CurrencyNbrbResponse;
import by.vasyabylba.currencyrates.model.dto.client.RateNbrbResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@FeignClient(name = "nbrb", url = "${external_api.url}", path = "/exrates")
public interface NbrbExratesClient {
    @GetMapping(path = "/rates", params = "periodicity=0")
    List<RateNbrbResponse> getRates(
            @RequestParam("ondate")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate date);

    @GetMapping(path = "/currencies", params = "periodicity=0")
    List<CurrencyNbrbResponse> getCurrencies();
}
