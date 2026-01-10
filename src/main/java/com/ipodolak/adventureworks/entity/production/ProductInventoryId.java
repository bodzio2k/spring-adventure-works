package com.ipodolak.adventureworks.entity.production;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductInventoryId implements Serializable {

    private Integer productId;
    private Short locationId;
}
