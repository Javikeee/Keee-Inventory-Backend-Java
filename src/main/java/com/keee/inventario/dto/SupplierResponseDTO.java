package com.keee.inventario.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SupplierResponseDTO {

    private Long id;
    private String companyName;
    private String contactName;
    private String telephone;
    private String email;
    private boolean isActive;

}
