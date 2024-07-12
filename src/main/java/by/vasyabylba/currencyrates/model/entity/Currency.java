package by.vasyabylba.currencyrates.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "Currency")
@Table(name = "currency")
public class Currency {
    @Id
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "parent_id", nullable = false)
    private int parentId;

    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "abbreviation", nullable = false)
    private String abbreviation;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "name_bel", nullable = false)
    private String nameBel;

    @Column(name = "name_eng", nullable = false)
    private String nameEng;

    @Column(name = "quot_name", nullable = false)
    private String quotName;

    @Column(name = "quot_name_bel", nullable = false)
    private String quotNameBel;

    @Column(name = "quot_name_eng", nullable = false)
    private String quotNameEng;

    @Column(name = "name_multi", nullable = false)
    private String nameMulti;

    @Column(name = "name_bel_multi", nullable = false)
    private String nameBelMulti;

    @Column(name = "name_eng_multi", nullable = false)
    private String nameEngMulti;

    @Column(name = "scale", nullable = false)
    private String scale;

    @Column(name = "periodicity", nullable = false)
    private int periodicity;

    @Column(name = "date_start", nullable = false)
    private LocalDate dateStart;

    @Column(name = "date_end", nullable = false)
    private LocalDate dateEnd;
}