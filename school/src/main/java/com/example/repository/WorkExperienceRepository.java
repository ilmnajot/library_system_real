package com.example.repository;

import com.example.entity.WorkExperience;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface WorkExperienceRepository extends JpaRepository<WorkExperience,Integer> {
    Optional<WorkExperience> findByPlaceOfWorkAndPositionAndEmployeeIdAndStartDateAndEndDate(String placeOfWork, String position, Integer employee_id, LocalDate startDate, LocalDate endDate);
    Page<WorkExperience> findAllByEmployeeId(int employeeId, Pageable pageable);

}
