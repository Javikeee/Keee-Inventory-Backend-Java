package com.keee.inventario.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductDTO {

    private String name;
    private String description;
    private Integer stockMinimum;
    private Double price;
    private Long categoryId;
    private Long supplierId;

}
