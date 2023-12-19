package com.example.repository;

import com.example.entity.Salary;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SalaryRepository extends JpaRepository<Salary,Integer> {

    List<Salary> findAllByUserIdAndActiveTrue(Integer userId, Sort sort);

    Optional<Salary> findByUserIdAndActiveTrue(Integer userId);

    List<Salary> findAllByBranch_IdAndActiveTrue(Integer branchId);

    Optional<Salary> findByUserPhoneNumberAndActiveTrue(String phoneNumber);
}
