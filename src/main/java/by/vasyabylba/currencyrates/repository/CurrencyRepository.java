package by.vasyabylba.currencyrates.repository;

import by.vasyabylba.currencyrates.model.entity.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyRepository extends JpaRepository<Currency, Integer> {
}