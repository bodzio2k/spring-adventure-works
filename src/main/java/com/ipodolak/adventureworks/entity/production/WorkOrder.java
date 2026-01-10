package com.ipodolak.adventureworks.entity.production;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "WorkOrder", schema = "Production")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "WorkOrderID")
    private Integer workOrderId;

    @Column(name = "ProductID", nullable = false)
    private Integer productId;

    @Column(name = "OrderQty", nullable = false)
    private Integer orderQty;

    @Column(name = "StockedQty", nullable = false)
    private Integer stockedQty;

    @Column(name = "ScrappedQty", nullable = false)
    private Short scrappedQty;

    @Column(name = "StartDate", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "EndDate")
    private LocalDateTime endDate;

    @Column(name = "DueDate", nullable = false)
    private LocalDateTime dueDate;

    @Column(name = "ScrapReasonID")
    private Short scrapReasonId;

    @Column(name = "ModifiedDate", nullable = false)
    private LocalDateTime modifiedDate;

    @ManyToOne
    @JoinColumn(name = "ProductID", insertable = false, updatable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "ScrapReasonID", insertable = false, updatable = false)
    private ScrapReason scrapReason;

    @PrePersist
    protected void onCreate() {
        if (modifiedDate == null) {
            modifiedDate = LocalDateTime.now();
        }
        if (startDate == null) {
            startDate = LocalDateTime.now();
        }
        if (stockedQty == null) {
            stockedQty = 0;
        }
        if (scrappedQty == null) {
            scrappedQty = 0;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        modifiedDate = LocalDateTime.now();
    }
}
