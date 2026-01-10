package com.ipodolak.adventureworks.entity.production;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "Location", schema = "Production")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LocationID")
    private Short locationId;

    @Column(name = "Name", nullable = false, length = 50, unique = true)
    private String name;

    @Column(name = "CostRate", nullable = false, precision = 10, scale = 4)
    private BigDecimal costRate;

    @Column(name = "Availability", nullable = false, precision = 8, scale = 2)
    private BigDecimal availability;

    @Column(name = "ModifiedDate", nullable = false)
    private LocalDateTime modifiedDate;

    @PrePersist
    protected void onCreate() {
        if (modifiedDate == null) {
            modifiedDate = LocalDateTime.now();
        }
        if (costRate == null) {
            costRate = BigDecimal.ZERO;
        }
        if (availability == null) {
            availability = BigDecimal.ZERO;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        modifiedDate = LocalDateTime.now();
    }
}
