package com.ipodolak.adventureworks.entity.production;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "BillOfMaterials", schema = "Production")
@NoArgsConstructor
@AllArgsConstructor
public class BillOfMaterials {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BillOfMaterialsID")
    private Integer billOfMaterialsId;

    @Column(name = "ProductAssemblyID")
    private Integer productAssemblyId;

    @Column(name = "ComponentID", nullable = false)
    private Integer componentId;

    @Column(name = "StartDate", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "EndDate")
    private LocalDateTime endDate;

    @Column(name = "UnitMeasureCode", nullable = false, length = 3)
    private String unitMeasureCode;

    @Column(name = "BOMLevel", nullable = false)
    private Short bomLevel;

    @Column(name = "PerAssemblyQty", nullable = false, precision = 8, scale = 2)
    private BigDecimal perAssemblyQty;

    @Column(name = "ModifiedDate", nullable = false)
    private LocalDateTime modifiedDate;

    @ManyToOne
    @JoinColumn(name = "ProductAssemblyID", insertable = false, updatable = false)
    private Product productAssembly;

    @ManyToOne
    @JoinColumn(name = "ComponentID", insertable = false, updatable = false)
    private Product component;

    @ManyToOne
    @JoinColumn(name = "UnitMeasureCode", referencedColumnName = "UnitMeasureCode", insertable = false, updatable = false)
    private UnitOfMeasure unitMeasure;

    @PrePersist
    protected void onCreate() {
        if (modifiedDate == null) {
            modifiedDate = LocalDateTime.now();
        }
        if (startDate == null) {
            startDate = LocalDateTime.now();
        }
        if (perAssemblyQty == null) {
            perAssemblyQty = BigDecimal.ONE;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        modifiedDate = LocalDateTime.now();
    }
}
