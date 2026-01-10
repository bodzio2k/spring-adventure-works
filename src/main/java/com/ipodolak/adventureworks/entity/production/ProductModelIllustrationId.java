package com.ipodolak.adventureworks.entity.production;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductModelIllustrationId implements Serializable {

    private Integer productModelId;
    private Integer illustrationId;
}
