package by.vasyabylba.currencyrates.mapper;

import by.vasyabylba.currencyrates.externalapi.dto.RateNbrbResponse;
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
}