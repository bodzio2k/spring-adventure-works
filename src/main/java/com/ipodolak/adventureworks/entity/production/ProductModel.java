package com.ipodolak.adventureworks.entity.production;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "ProductModel", schema = "Production")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ProductModelID")
    private Integer productModelId;

    @Column(name = "Name", nullable = false, length = 50, unique = true)
    private String name;

    @Column(name = "CatalogDescription", columnDefinition = "xml")
    private String catalogDescription;

    @Column(name = "Instructions", columnDefinition = "xml")
    private String instructions;

    @Column(name = "rowguid", nullable = false, unique = true)
    private UUID rowguid;

    @Column(name = "ModifiedDate", nullable = false)
    private LocalDateTime modifiedDate;

    @PrePersist
    protected void onCreate() {
        if (rowguid == null) {
            rowguid = UUID.randomUUID();
        }
        if (modifiedDate == null) {
            modifiedDate = LocalDateTime.now();
        }
    }

    @PreUpdate
    protected void onUpdate() {
        modifiedDate = LocalDateTime.now();
    }
}
