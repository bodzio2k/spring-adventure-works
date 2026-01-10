package com.ipodolak.adventureworks.entity.production;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "Document", schema = "Production")
@NoArgsConstructor
@AllArgsConstructor
public class Document {

    @Id
    @Column(name = "DocumentNode", columnDefinition = "hierarchyid")
    private String documentNode;

    @Column(name = "DocumentLevel")
    private Short documentLevel;

    @Column(name = "Title", nullable = false, length = 50)
    private String title;

    @Column(name = "Owner", nullable = false)
    private Integer owner;

    @Column(name = "FolderFlag", nullable = false)
    private Boolean folderFlag;

    @Column(name = "FileName", nullable = false, length = 400, unique = true)
    private String fileName;

    @Column(name = "FileExtension", length = 8)
    private String fileExtension;

    @Column(name = "Revision", nullable = false, length = 5)
    private String revision;

    @Column(name = "ChangeNumber", nullable = false)
    private Integer changeNumber;

    @Column(name = "Status", nullable = false)
    private Short status;

    @Column(name = "DocumentSummary")
    private String documentSummary;

    @Lob
    @Column(name = "Document", columnDefinition = "varbinary(max)")
    private byte[] document;

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
        if (folderFlag == null) {
            folderFlag = false;
        }
        if (changeNumber == null) {
            changeNumber = 0;
        }
        if (status == null) {
            status = 1;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        modifiedDate = LocalDateTime.now();
    }
}
