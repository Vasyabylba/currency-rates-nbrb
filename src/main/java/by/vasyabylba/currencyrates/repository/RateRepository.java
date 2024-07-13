package by.vasyabylba.currencyrates.repository;

import by.vasyabylba.currencyrates.model.entity.Rate;
import by.vasyabylba.currencyrates.model.entity.RateId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RateRepository extends JpaRepository<Rate, RateId> {
}