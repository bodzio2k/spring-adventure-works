package com.ipodolak.adventureworks.entity.production;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "ProductInventory", schema = "Production")
@IdClass(ProductInventoryId.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductInventory {

    @Id
    @Column(name = "ProductID")
    private Integer productId;

    @Id
    @Column(name = "LocationID")
    private Short locationId;

    @Column(name = "Shelf", nullable = false, length = 10)
    private String shelf;

    @Column(name = "Bin", nullable = false)
    private Short bin;

    @Column(name = "Quantity", nullable = false)
    private Short quantity;

    @Column(name = "rowguid", nullable = false, unique = true)
    private UUID rowguid;

    @Column(name = "ModifiedDate", nullable = false)
    private LocalDateTime modifiedDate;

    @ManyToOne
    @JoinColumn(name = "ProductID", insertable = false, updatable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "LocationID", insertable = false, updatable = false)
    private Location location;

    @PrePersist
    protected void onCreate() {
        if (rowguid == null) {
            rowguid = UUID.randomUUID();
        }
        if (modifiedDate == null) {
            modifiedDate = LocalDateTime.now();
        }
        if (quantity == null) {
            quantity = 0;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        modifiedDate = LocalDateTime.now();
    }
}
