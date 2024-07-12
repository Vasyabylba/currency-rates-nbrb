package by.vasyabylba.currencyrates.mapper;

import by.vasyabylba.currencyrates.externalapi.dto.CurrencyNbrbResponse;
import by.vasyabylba.currencyrates.model.entity.Currency;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface CurrencyMapper {
    @Mapping(source = "curDateEnd", target = "dateEnd")
    @Mapping(source = "curDateStart", target = "dateStart")
    @Mapping(source = "curPeriodicity", target = "periodicity")
    @Mapping(source = "curScale", target = "scale")
    @Mapping(source = "curNameEngMulti", target = "nameEngMulti")
    @Mapping(source = "curNameBelMulti", target = "nameBelMulti")
    @Mapping(source = "curNameMulti", target = "nameMulti")
    @Mapping(source = "curQuotNameEng", target = "quotNameEng")
    @Mapping(source = "curQuotNameBel", target = "quotNameBel")
    @Mapping(source = "curQuotName", target = "quotName")
    @Mapping(source = "curNameEng", target = "nameEng")
    @Mapping(source = "curNameBel", target = "nameBel")
    @Mapping(source = "curName", target = "name")
    @Mapping(source = "curAbbreviation", target = "abbreviation")
    @Mapping(source = "curCode", target = "code")
    @Mapping(source = "curParentId", target = "parentId")
    @Mapping(source = "curId", target = "id")
    Currency toCurrency(CurrencyNbrbResponse currencyNbrbResponse);

    @InheritInverseConfiguration(name = "toCurrency")
    CurrencyNbrbResponse toCurrencyNbrbResponse(Currency currency);

    @InheritConfiguration(name = "toCurrency")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Currency partialUpdate(CurrencyNbrbResponse currencyNbrbResponse, @MappingTarget Currency currency);
}