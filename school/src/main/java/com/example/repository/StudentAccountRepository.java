package com.example.repository;

import com.example.entity.StudentAccount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StudentAccountRepository extends JpaRepository<StudentAccount, Integer> {
    Optional<StudentAccount> findByAccountNumberAndActiveTrue(String accountNumber);

    Optional<StudentAccount> findByStudentIdAndActiveTrue(Integer student_id);

    Optional<StudentAccount> findByIdAndActiveTrue(Integer id);

    Page<StudentAccount> findAllByBranch_IdAndActiveTrue(Integer branch_id, Pageable pageable);

    Optional<StudentAccount> findAllByStudent_Id(Integer studentId);

    Page<StudentAccount> findAllByStudent_StudentClass_IdAndActiveTrue(Integer student_studentClass_id, Pageable pageable);

    List<StudentAccount> findAllByActiveTrueAndAmountOfDebitIsNotNull(Sort sort);


    List<StudentAccount> findAllByBranch_IdAndActiveTrueAndAmountOfDebitGreaterThan(Integer branch_id, double amountOfDebit);

    List<StudentAccount> findAllByActiveTrue(Sort sort);

    List<StudentAccount> findAllByAccountNumberContainingIgnoreCaseAndStudent_ActiveTrueAndActiveTrue(String accountNumber);

    List<StudentAccount> findAllByStudent_LastNameContainingIgnoreCaseAndStudent_ActiveTrueAndActiveTrue(String lastname);

    List<StudentAccount> findAllByStudentFirstNameContainingIgnoreCaseAndStudent_ActiveTrueAndActiveTrue(String lastname);
}
