package com.example.repository;

import com.example.entity.OverallReport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface OverallReportRepository extends JpaRepository<OverallReport, Integer> {

    Page<OverallReport> findAllByDateBetween(LocalDate date, LocalDate dateEnd, Pageable pageable);

    Page<OverallReport> findAllByBranch_Id(Integer branchId, Pageable pageable);
}
