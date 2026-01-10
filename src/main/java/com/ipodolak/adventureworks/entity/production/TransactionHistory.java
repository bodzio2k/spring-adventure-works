package com.ipodolak.adventureworks.entity.production;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "TransactionHistory", schema = "Production")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TransactionID")
    private Integer transactionId;

    @Column(name = "ProductID", nullable = false)
    private Integer productId;

    @Column(name = "ReferenceOrderID", nullable = false)
    private Integer referenceOrderId;

    @Column(name = "ReferenceOrderLineID", nullable = false)
    private Integer referenceOrderLineId;

    @Column(name = "TransactionDate", nullable = false)
    private LocalDateTime transactionDate;

    @Column(name = "TransactionType", nullable = false, length = 1)
    private String transactionType;

    @Column(name = "Quantity", nullable = false)
    private Integer quantity;

    @Column(name = "ActualCost", nullable = false, precision = 19, scale = 4)
    private BigDecimal actualCost;

    @Column(name = "ModifiedDate", nullable = false)
    private LocalDateTime modifiedDate;

    @ManyToOne
    @JoinColumn(name = "ProductID", insertable = false, updatable = false)
    private Product product;

    @PrePersist
    protected void onCreate() {
        if (modifiedDate == null) {
            modifiedDate = LocalDateTime.now();
        }
        if (transactionDate == null) {
            transactionDate = LocalDateTime.now();
        }
    }

    @PreUpdate
    protected void onUpdate() {
        modifiedDate = LocalDateTime.now();
    }
}
