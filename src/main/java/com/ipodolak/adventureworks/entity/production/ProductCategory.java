package com.ipodolak.adventureworks.entity.production;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "ProductCategory", schema = "Production")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ProductCategoryID")
    private Integer productCategoryId;

    @Column(name = "Name", nullable = false, length = 50, unique = true)
    private String name;

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
