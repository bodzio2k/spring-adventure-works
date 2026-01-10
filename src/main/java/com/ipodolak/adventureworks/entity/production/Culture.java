package com.ipodolak.adventureworks.entity.production;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Table(name = "Culture", schema = "Production")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Culture {

    @Id
    @Column(name = "CultureID", length = 6)
    private String cultureId;

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
