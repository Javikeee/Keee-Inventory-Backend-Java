package com.keee.inventario.dto;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class RoleRequestDTO {

    private String name;
    private String description;
    private List<Long> permissionIds;

}
