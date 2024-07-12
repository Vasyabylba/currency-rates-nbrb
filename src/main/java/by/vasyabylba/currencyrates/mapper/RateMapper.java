package by.vasyabylba.currencyrates.mapper;

import by.vasyabylba.currencyrates.externalapi.dto.RateNbrbResponse;
import by.vasyabylba.currencyrates.model.dto.RateResponse;
import by.vasyabylba.currencyrates.model.entity.Currency;
import by.vasyabylba.currencyrates.model.entity.Rate;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface RateMapper {
    @Mapping(source = "curOfficialRate", target = "officialRate")
    @Mapping(source = "date", target = "id.date")
    @Mapping(source = "curId", target = "id.curId")
    Rate toRate(RateNbrbResponse rateNbrbResponse);

    @InheritInverseConfiguration(name = "toRate")
    RateNbrbResponse toRateNbrbResponse(Rate rate);

    @InheritConfiguration(name = "toRate")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Rate partialUpdate(RateNbrbResponse rateNbrbResponse, @MappingTarget Rate rate);

    @Mapping(source = "currency.id", target = "curId")
    @Mapping(source = "rate.id.date", target = "date")
    @Mapping(source = "currency.abbreviation", target = "curAbbreviation")
    @Mapping(source = "currency.scale", target = "curScale")
    @Mapping(source = "currency.name", target = "curName")
    @Mapping(source = "rate.officialRate", target = "curOfficialRate")
    RateResponse toRateResponse(Rate rate, Currency currency);
}