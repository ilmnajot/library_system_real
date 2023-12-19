package com.example.kitchen.repository;

import com.example.kitchen.entity.Warehouse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WareHouseRepository extends JpaRepository<Warehouse, Integer> {
    Optional<Warehouse> findByIdAndActiveTrue(Integer id);

    Optional<Warehouse> findByNameAndActiveTrueAndBranchId(String name, Integer branch_id);

    Page<Warehouse> findAllByActiveTrueAndBranchId(Integer branch_id, Pageable pageable);
    Page<Warehouse> findAllByActiveTrue(Pageable pageable);
}
