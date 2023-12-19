package com.example.repository;

import com.example.entity.StaffAttendance;
import com.example.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface StaffAttendanceRepository extends JpaRepository<StaffAttendance,Integer> {

    Page<StaffAttendance> findAllByUserId(Integer id, Pageable pageable);

    Optional<StaffAttendance> findByUserIdAndDate(Integer user_id, LocalDate date);
    Optional<StaffAttendance> findByUserId(Integer user_id);

    Page<StaffAttendance> findAllByBranchId(Integer id,Pageable pageable);
}
