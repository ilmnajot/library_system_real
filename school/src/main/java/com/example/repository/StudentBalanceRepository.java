package com.example.repository;

import com.example.entity.StudentAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentBalanceRepository extends JpaRepository<StudentAccount, Integer> {


    Optional<StudentAccount> findByStudentIdAndActiveTrue(Integer integer);
    Optional<StudentAccount> findByStudentId(Integer integer);

}
