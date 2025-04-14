package com.keee.inventario.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PermissionDTO {

    private Long id;
    private String name;
    private String description;
    private boolean active;

}
