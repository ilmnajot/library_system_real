package com.example.kitchen.repository;

import com.example.enums.MeasurementType;
import com.example.kitchen.entity.ProductsInWareHouse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductsInWareHouseRepository extends JpaRepository<ProductsInWareHouse, Integer> {
    Optional<ProductsInWareHouse> findByNameAndMeasurementTypeAndBranchIdAndWarehouseId(String name, MeasurementType measurementType, Integer branchId, Integer wareHouseId);

    Optional<ProductsInWareHouse> findByIdAndActiveTrue(Integer productInWarehouseId);

    Page<ProductsInWareHouse> findAllByWarehouseIdAndActiveTrue(Integer wareHouseID, PageRequest of);

    Page<ProductsInWareHouse> findAllByBranchIdAndActiveTrue(Integer branchId, PageRequest of);
}
