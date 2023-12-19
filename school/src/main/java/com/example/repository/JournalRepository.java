package com.example.repository;

import com.example.entity.Journal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface JournalRepository extends JpaRepository<Journal, Integer> {

    Page<Journal> findAllByBranchIdAndActiveTrue(Integer branchId, Pageable pageable);

    Optional<Journal> findByStudentClassIdAndActiveTrue(Integer studentClass_id);

    boolean existsByStudentClassIdAndActiveTrue(Integer studentClassId);

    Optional<Journal> findByIdAndActiveTrue(Integer integer);
}
