package com.ipodolak.adventureworks.entity.production;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "ProductModelProductDescriptionCulture", schema = "Production")
@IdClass(ProductModelDescriptionId.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductModelDescription {

    @Id
    @Column(name = "ProductModelID")
    private Integer productModelId;

    @Id
    @Column(name = "CultureID", length = 6)
    private String cultureId;

    @Column(name = "ProductDescriptionID", nullable = false)
    private Integer productDescriptionId;

    @Column(name = "ModifiedDate", nullable = false)
    private LocalDateTime modifiedDate;

    @ManyToOne
    @JoinColumn(name = "ProductModelID", insertable = false, updatable = false)
    private ProductModel productModel;

    @ManyToOne
    @JoinColumn(name = "CultureID", insertable = false, updatable = false)
    private Culture culture;

    @PrePersist
    protected void onCreate() {
        if (modifiedDate == null) {
            modifiedDate = LocalDateTime.now();
        }
    }

    @PreUpdate
    protected void onUpdate() {
        modifiedDate = LocalDateTime.now();
    }
}
