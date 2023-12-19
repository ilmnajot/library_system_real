package com.example.repository;

import com.example.entity.Business;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface BusinessRepository extends JpaRepository<Business, Integer> {

    boolean existsByName(String name);

    Page<Business> findAllByDeleteFalse(Pageable pageable);
    Page<Business> findAllByActiveTrueAndDeleteFalse(Pageable pageable);

    Optional<Business> findByIdAndActiveTrueAndDeleteFalse(Integer businessId);
}
