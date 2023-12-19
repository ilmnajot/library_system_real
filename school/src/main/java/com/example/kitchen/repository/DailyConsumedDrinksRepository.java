package com.example.kitchen.repository;

import com.example.kitchen.entity.DailyConsumedDrinks;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DailyConsumedDrinksRepository extends JpaRepository<DailyConsumedDrinks,Integer> {
    Optional<DailyConsumedDrinks> findByIdAndDeleteFalse(Integer integer);

    Page<DailyConsumedDrinks> findAllByBranch_IdAndDeleteFalse(Integer branchId, Pageable pageable);
    Page<DailyConsumedDrinks> findAllByWarehouseIdAndDeleteFalse(Integer warehouseId, Pageable pageable);
}
