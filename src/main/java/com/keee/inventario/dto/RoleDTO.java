package com.keee.inventario.dto;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RoleDTO {

    private Long id;
    private String name;

}
