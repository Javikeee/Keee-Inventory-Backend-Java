package com.keee.inventario.repository;

import com.keee.inventario.entity.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolePermissionRepository extends JpaRepository<RolePermission, Long> {
}
