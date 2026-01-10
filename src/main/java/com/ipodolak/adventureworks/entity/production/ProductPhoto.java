package com.ipodolak.adventureworks.entity.production;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "ProductPhoto", schema = "Production")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductPhoto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ProductPhotoID")
    private Integer productPhotoId;

    @Lob
    @Column(name = "ThumbNailPhoto", columnDefinition = "varbinary(max)")
    private byte[] thumbNailPhoto;

    @Column(name = "ThumbnailPhotoFileName", length = 50)
    private String thumbnailPhotoFileName;

    @Lob
    @Column(name = "LargePhoto", columnDefinition = "varbinary(max)")
    private byte[] largePhoto;

    @Column(name = "LargePhotoFileName", length = 50)
    private String largePhotoFileName;

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
