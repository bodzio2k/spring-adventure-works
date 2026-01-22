package com.ipodolak.adventureworks.mapper;

import com.adventureworks.model.Product.ProductLineEnum;
import com.adventureworks.model.Product.PropertyClassEnum;
import com.adventureworks.model.Product.StyleEnum;
import com.ipodolak.adventureworks.entity.production.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.openapitools.jackson.nullable.JsonNullable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "propertyClass", source = "productClass", qualifiedByName = "stringToPropertyClassEnum")
    @Mapping(target = "productLine", source = "productLine", qualifiedByName = "stringToProductLineEnum")
    @Mapping(target = "style", source = "style", qualifiedByName = "stringToStyleEnum")
    @Mapping(target = "standardCost", source = "standardCost", qualifiedByName = "bigDecimalToDouble")
    @Mapping(target = "listPrice", source = "listPrice", qualifiedByName = "bigDecimalToDouble")
    @Mapping(target = "weight", source = "weight", qualifiedByName = "bigDecimalToJsonNullableDouble")
    @Mapping(target = "sellStartDate", source = "sellStartDate", qualifiedByName = "localDateTimeToOffsetDateTime")
    @Mapping(target = "sellEndDate", source = "sellEndDate", qualifiedByName = "localDateTimeToJsonNullableOffsetDateTime")
    @Mapping(target = "discontinuedDate", source = "discontinuedDate", qualifiedByName = "localDateTimeToJsonNullableOffsetDateTime")
    @Mapping(target = "color", source = "color", qualifiedByName = "stringToJsonNullable")
    @Mapping(target = "size", source = "size", qualifiedByName = "stringToJsonNullable")
    @Mapping(target = "sizeUnitMeasureCode", source = "sizeUnitMeasureCode", qualifiedByName = "stringToJsonNullable")
    @Mapping(target = "weightUnitMeasureCode", source = "weightUnitMeasureCode", qualifiedByName = "stringToJsonNullable")
    @Mapping(target = "productSubcategoryId", source = "productSubcategoryId", qualifiedByName = "integerToJsonNullable")
    @Mapping(target = "productModelId", source = "productModelId", qualifiedByName = "integerToJsonNullable")
    com.adventureworks.model.Product toDto(Product entity);

    List<com.adventureworks.model.Product> toDtoList(List<Product> entities);

    // === String to Enum mappings ===

    @Named("stringToProductLineEnum")
    default JsonNullable<ProductLineEnum> stringToProductLineEnum(String value) {
        if (value == null) {
            return JsonNullable.of(null);
        }
        return JsonNullable.of(ProductLineEnum.fromValue(value.trim()));
    }

    @Named("stringToPropertyClassEnum")
    default JsonNullable<PropertyClassEnum> stringToPropertyClassEnum(String value) {
        if (value == null) {
            return JsonNullable.of(null);
        }
        return JsonNullable.of(PropertyClassEnum.fromValue(value.trim()));
    }

    @Named("stringToStyleEnum")
    default JsonNullable<StyleEnum> stringToStyleEnum(String value) {
        if (value == null) {
            return JsonNullable.of(null);
        }
        return JsonNullable.of(StyleEnum.fromValue(value.trim()));
    }

    // === BigDecimal to Double mappings ===

    @Named("bigDecimalToDouble")
    default Double bigDecimalToDouble(BigDecimal value) {
        return value != null ? value.doubleValue() : null;
    }

    @Named("bigDecimalToJsonNullableDouble")
    default JsonNullable<Double> bigDecimalToJsonNullableDouble(BigDecimal value) {
        if (value == null) {
            return JsonNullable.of(null);
        }
        return JsonNullable.of(value.doubleValue());
    }

    // === DateTime mappings ===

    @Named("localDateTimeToOffsetDateTime")
    default OffsetDateTime localDateTimeToOffsetDateTime(LocalDateTime value) {
        if (value == null) {
            return null;
        }
        return value.atOffset(ZoneOffset.UTC);
    }

    @Named("localDateTimeToJsonNullableOffsetDateTime")
    default JsonNullable<OffsetDateTime> localDateTimeToJsonNullableOffsetDateTime(LocalDateTime value) {
        if (value == null) {
            return JsonNullable.of(null);
        }
        return JsonNullable.of(value.atOffset(ZoneOffset.UTC));
    }

    // === JsonNullable wrapper mappings ===

    @Named("stringToJsonNullable")
    default JsonNullable<String> stringToJsonNullable(String value) {
        if (value == null) {
            return JsonNullable.of(null);
        }
        return JsonNullable.of(value);
    }

    @Named("integerToJsonNullable")
    default JsonNullable<Integer> integerToJsonNullable(Integer value) {
        if (value == null) {
            return JsonNullable.of(null);
        }
        return JsonNullable.of(value);
    }
}
