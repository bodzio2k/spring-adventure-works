package com.ipodolak.adventureworks.entity.production;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "UnitMeasure", schema = "Production")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UnitOfMeasure {

    @Id
    @Column(name = "UnitMeasureCode", length = 3)
    private String unitMeasureCode;

    @Column(name = "Name", nullable = false, length = 50, unique = true)
    private String name;

    @Column(name = "ModifiedDate", nullable = false)
    private LocalDateTime modifiedDate;

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
