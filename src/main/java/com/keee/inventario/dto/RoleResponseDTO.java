package com.keee.inventario.dto;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class RoleResponseDTO {

    private Long id;
    private String name;
    private String description;
    private boolean active;
    private List<PermissionDTO> permissions;

}
