package com.example.kitchen.repository;

import com.example.kitchen.entity.PurchasedProducts;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PurchasedProductsRepository extends JpaRepository<PurchasedProducts,Integer> {
    Optional<PurchasedProducts> findByIdAndDeleteFalse(Integer integer);
    Page<PurchasedProducts> findAllByBranch_IdAndDeleteFalse(Integer integer, Pageable pageable);
    Page<PurchasedProducts> findAllByWarehouseIdAndDeleteFalse(Integer integer, Pageable pageable);
}
