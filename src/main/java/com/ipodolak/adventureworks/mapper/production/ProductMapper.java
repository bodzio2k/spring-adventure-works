package com.ipodolak.adventureworks.mapper.production;

import com.ipodolak.adventureworks.dto.production.ProductDto;
import com.ipodolak.adventureworks.entity.production.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductMapper {

    @Mapping(source = "productSubcategory.name", target = "subcategoryName")
    @Mapping(source = "productSubcategory.productCategory.name", target = "categoryName")
    @Mapping(source = "productModel.name", target = "modelName")
    ProductDto toDto(Product product);

    @Mapping(target = "rowguid", ignore = true)
    @Mapping(target = "modifiedDate", ignore = true)
    @Mapping(target = "productSubcategory", ignore = true)
    @Mapping(target = "productModel", ignore = true)
    @Mapping(target = "sizeUnitMeasure", ignore = true)
    @Mapping(target = "weightUnitMeasure", ignore = true)
    @Mapping(target = "productSubcategoryId", ignore = true)
    @Mapping(target = "productModelId", ignore = true)
    Product toEntity(ProductDto dto);
}
