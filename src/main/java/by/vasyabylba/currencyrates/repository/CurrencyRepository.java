package by.vasyabylba.currencyrates.repository;

import by.vasyabylba.currencyrates.model.entity.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.Optional;

public interface CurrencyRepository extends JpaRepository<Currency, Integer> {
    @Query(value = """
            SELECT cur \
            FROM Currency cur \
            WHERE cur.code = :code and :date BETWEEN cur.dateStart and cur.dateEnd""")
    Optional<Currency> findByCodeAndDate(String code, LocalDate date);

    @Query(value = """
            SELECT cur \
            FROM Currency cur \
            WHERE cur.abbreviation = :abbreviation and :date BETWEEN cur.dateStart and cur.dateEnd""")
    Optional<Currency> findByAbbreviationAndDate(String abbreviation, LocalDate date);

    @Query(value = """
            SELECT cur \
            FROM Currency cur \
            WHERE cur.id = :curId and :date BETWEEN cur.dateStart and cur.dateEnd""")
    Optional<Currency> findByCurIdAndDate(int curId, LocalDate date);
}