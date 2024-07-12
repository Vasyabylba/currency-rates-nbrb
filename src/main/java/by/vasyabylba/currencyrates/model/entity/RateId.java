package by.vasyabylba.currencyrates.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;
import org.hibernate.Hibernate;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Embeddable
public class RateId implements Serializable {
    @Serial
    private static final long serialVersionUID = -6315980305993068917L;
    @Column(name = "date")
    private LocalDate date;

    @Column(name = "cur_id", nullable = false)
    private int curId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        RateId entity = (RateId) o;
        return Objects.equals(this.date, entity.date) &&
                Objects.equals(this.curId, entity.curId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, curId);
    }
}