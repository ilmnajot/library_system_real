package com.example.repository;


import com.example.entity.Branch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BranchRepository extends JpaRepository<Branch, Integer> {

    Optional<Branch> findByBusinessIdAndAddress(Integer business_id, String address);
    List<Branch> findAllByBusinessIdAndDeleteFalse(Integer business_id, Sort sort);

    Page<Branch> findAllByDeleteFalse(Pageable pageable);

    Optional<Branch> findByIdAndDeleteFalse(Integer branchId);
}
