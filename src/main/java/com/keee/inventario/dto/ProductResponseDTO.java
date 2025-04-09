package com.keee.inventario.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductResponseDTO {

    private Long id;
    private String name;
    private String description;
    private int stockNow;
    private int stockMinimum;
    private double price;
    private CategoryDTO category;
    private SupplierDTO supplier;

}
