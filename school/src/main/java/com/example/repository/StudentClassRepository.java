package com.example.repository;

import com.example.entity.StudentClass;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface StudentClassRepository extends JpaRepository<StudentClass, Integer> {

    List<StudentClass> findAllByActiveTrueAndBranchId(Integer branchId, Sort sort);
    List<StudentClass> findAllByBranchIdAndStartDateAfterAndEndDateBeforeAndActiveFalse(Integer branchId, LocalDate startDate, LocalDate endDate,Sort sort);

    Optional<StudentClass> findByClassLeaderIdAndActiveTrue(Integer classLeaderId);
    Optional<StudentClass> findByClassLeaderPhoneNumberAndActiveTrue(String phoneNumber);

    Optional<StudentClass> findByIdAndActiveTrue(Integer studentClassId);
}
