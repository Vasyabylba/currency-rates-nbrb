package by.vasyabylba.currencyrates.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "Rate")
@Table(name = "rate")
public class Rate {
    @EmbeddedId
    private RateId id;

    @Column(name = "official_rate")
    private String officialRate;
}