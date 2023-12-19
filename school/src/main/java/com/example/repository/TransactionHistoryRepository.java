package com.example.repository;

import com.example.entity.TransactionHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TransactionHistoryRepository extends JpaRepository<TransactionHistory,Integer> {
    Optional<TransactionHistory> findByIdAndActiveTrue(Integer integer);
    Page<TransactionHistory> findAllByStudentIdAndActiveTrue(Integer student_id, Pageable pageable);
    Page<TransactionHistory> findAllByBranch_IdAndStudentNotNullAndActiveTrue(Integer student_id, Pageable pageable);
    Optional<TransactionHistory> findFirstByStudentIdAndActiveTrue(Integer integer,Sort sort);
    List<TransactionHistory> findAllByBranch_IdAndActiveTrue(Integer branchId, Sort sort);
    Page<TransactionHistory> findAllByBranchIdAndActiveTrue(Integer branchI,
                                                            Pageable pageable);
    List<TransactionHistory> findAllByActiveTrue(Sort sort);
}
