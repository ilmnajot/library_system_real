package com.example.repository;

import com.example.entity.MainBalance;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MainBalanceRepository extends JpaRepository<MainBalance,Integer> {
    Optional<MainBalance> findByIdAndActiveTrue(Integer integer);

    List<MainBalance> findAllByBranch_IdAndActiveTrue(Integer branchId, Sort sort);
}
