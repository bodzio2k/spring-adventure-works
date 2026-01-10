package com.ipodolak.adventureworks.entity.production;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "ProductModelIllustration", schema = "Production")
@IdClass(ProductModelIllustrationId.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductModelIllustration {

    @Id
    @Column(name = "ProductModelID")
    private Integer productModelId;

    @Id
    @Column(name = "IllustrationID")
    private Integer illustrationId;

    @Column(name = "ModifiedDate", nullable = false)
    private LocalDateTime modifiedDate;

    @ManyToOne
    @JoinColumn(name = "ProductModelID", insertable = false, updatable = false)
    private ProductModel productModel;

    @ManyToOne
    @JoinColumn(name = "IllustrationID", insertable = false, updatable = false)
    private Illustration illustration;

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
