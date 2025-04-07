package com.keee.inventario.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductRequestDTO {

    @NotEmpty(message = "Name cannot be empty")
    private String name;

    @NotEmpty(message = "Description cannot be empty")
    private String description;

    @NotEmpty(message = "Stock minimum cannot be empty")
    private int stockMinimum;

    @NotEmpty(message = "Price cannot be empty")
    private double price;

    @NotEmpty(message = "Category cannot be empty")
    private Long categoryId;

    @NotEmpty(message = "Supplier cannot be empty")
    private Long supplierId;
}
