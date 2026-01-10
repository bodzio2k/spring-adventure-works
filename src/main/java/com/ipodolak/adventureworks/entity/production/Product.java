package com.ipodolak.adventureworks.entity.production;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "Product", schema = "Production")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ProductID")
    private Integer productId;

    @Column(name = "Name", nullable = false, length = 50, unique = true)
    private String name;

    @Column(name = "ProductNumber", nullable = false, length = 25, unique = true)
    private String productNumber;

    @Column(name = "MakeFlag", nullable = false)
    private Boolean makeFlag;

    @Column(name = "FinishedGoodsFlag", nullable = false)
    private Boolean finishedGoodsFlag;

    @Column(name = "Color", length = 15)
    private String color;

    @Column(name = "SafetyStockLevel", nullable = false)
    private Short safetyStockLevel;

    @Column(name = "ReorderPoint", nullable = false)
    private Short reorderPoint;

    @Column(name = "StandardCost", nullable = false, precision = 19, scale = 4)
    private BigDecimal standardCost;

    @Column(name = "ListPrice", nullable = false, precision = 19, scale = 4)
    private BigDecimal listPrice;

    @Column(name = "Size", length = 5)
    private String size;

    @Column(name = "SizeUnitMeasureCode", length = 3)
    private String sizeUnitMeasureCode;

    @Column(name = "Weight", precision = 8, scale = 2)
    private BigDecimal weight;

    @Column(name = "WeightUnitMeasureCode", length = 3)
    private String weightUnitMeasureCode;

    @Column(name = "DaysToManufacture", nullable = false)
    private Integer daysToManufacture;

    @Column(name = "ProductLine", length = 2)
    private String productLine;

    @Column(name = "Class", length = 2)
    private String productClass;

    @Column(name = "Style", length = 2)
    private String style;

    @Column(name = "ProductSubcategoryID")
    private Integer productSubcategoryId;

    @Column(name = "ProductModelID")
    private Integer productModelId;

    @Column(name = "SellStartDate", nullable = false)
    private LocalDateTime sellStartDate;

    @Column(name = "SellEndDate")
    private LocalDateTime sellEndDate;

    @Column(name = "DiscontinuedDate")
    private LocalDateTime discontinuedDate;

    @Column(name = "rowguid", nullable = false, unique = true)
    private UUID rowguid;

    @Column(name = "ModifiedDate", nullable = false)
    private LocalDateTime modifiedDate;

    @ManyToOne
    @JoinColumn(name = "ProductSubcategoryID", insertable = false, updatable = false)
    private ProductSubcategory productSubcategory;

    @ManyToOne
    @JoinColumn(name = "ProductModelID", insertable = false, updatable = false)
    private ProductModel productModel;

    @ManyToOne
    @JoinColumn(name = "SizeUnitMeasureCode", referencedColumnName = "UnitMeasureCode", insertable = false, updatable = false)
    private UnitOfMeasure sizeUnitMeasure;

    @ManyToOne
    @JoinColumn(name = "WeightUnitMeasureCode", referencedColumnName = "UnitMeasureCode", insertable = false, updatable = false)
    private UnitOfMeasure weightUnitMeasure;

    @PrePersist
    protected void onCreate() {
        if (rowguid == null) {
            rowguid = UUID.randomUUID();
        }
        if (modifiedDate == null) {
            modifiedDate = LocalDateTime.now();
        }
        if (makeFlag == null) {
            makeFlag = true;
        }
        if (finishedGoodsFlag == null) {
            finishedGoodsFlag = true;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        modifiedDate = LocalDateTime.now();
    }
}
