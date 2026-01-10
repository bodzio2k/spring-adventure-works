package com.ipodolak.adventureworks.entity.production;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Illustration", schema = "Production")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Illustration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IllustrationID")
    private Integer illustrationId;

    @Column(name = "Diagram", columnDefinition = "xml")
    private String diagram;

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
