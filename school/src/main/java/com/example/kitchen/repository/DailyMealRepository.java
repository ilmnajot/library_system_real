package com.example.kitchen.repository;

import com.example.kitchen.entity.DailyMeal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DailyMealRepository extends JpaRepository<DailyMeal,Integer> {
    Page<DailyMeal> findAllByBranchId(Integer branchId, Pageable pageable);
}
