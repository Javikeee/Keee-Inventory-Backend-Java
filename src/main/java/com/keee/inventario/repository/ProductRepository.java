package com.keee.inventario.repository;

import com.keee.inventario.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findAllBySupplierId(Long supplierId);

    List<Product> findByActiveTrue();

}
