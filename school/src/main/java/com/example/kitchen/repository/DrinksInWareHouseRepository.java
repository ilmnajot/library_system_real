package com.example.kitchen.repository;

import com.example.kitchen.entity.DrinksInWareHouse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DrinksInWareHouseRepository extends JpaRepository<DrinksInWareHouse, Integer> {
    Optional<DrinksInWareHouse> findByNameAndLiterQuantityAndBranchIdAndWarehouseId(String name, int literQuantity, Integer branchId, Integer warehouseId);

    Optional<DrinksInWareHouse> findByIdAndActiveTrue(Integer integer);

    Page<DrinksInWareHouse> findAllByWarehouseIdAndActiveTrue(Integer drinksInWarehouseId, Pageable pageable);

    Page<DrinksInWareHouse> findAllByBranchIdAndActiveTrue(Integer branchId, Pageable pageable);
}
