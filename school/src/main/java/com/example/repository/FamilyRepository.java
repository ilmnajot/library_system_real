package com.example.repository;

import com.example.entity.Family;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface FamilyRepository extends JpaRepository<Family, Integer> {
    Page<Family> findAllByBranchIdAndActiveTrue(int branch_id, Pageable pageable);

    Optional<Family> findByPhoneNumberAndPassword(String phoneNumber, String password);

    Optional<Family> findByIdAndActiveTrue(Integer integer);

}
