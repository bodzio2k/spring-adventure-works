package com.ipodolak.adventureworks.dto.production;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private Integer productId;
    private String name;
    private String productNumber;
    private Boolean makeFlag;
    private Boolean finishedGoodsFlag;
    private String color;
    private Short safetyStockLevel;
    private Short reorderPoint;
    private BigDecimal standardCost;
    private BigDecimal listPrice;
    private String size;
    private String sizeUnitMeasureCode;
    private BigDecimal weight;
    private String weightUnitMeasureCode;
    private Integer daysToManufacture;
    private String productLine;
    private String productClass;
    private String style;
    private LocalDateTime sellStartDate;
    private LocalDateTime sellEndDate;
    private LocalDateTime discontinuedDate;

    // Nested objects for related entities
    private String subcategoryName;
    private String categoryName;
    private String modelName;
}
