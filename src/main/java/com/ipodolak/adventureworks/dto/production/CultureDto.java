package com.ipodolak.adventureworks.dto.production;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CultureDto {
    private String cultureId;
    private String name;
    private LocalDateTime modifiedDate;
}
