package com.example.repository;

import com.example.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Integer> {

    List<Student> findAllByStudentClassIdAndBranchIdAndActiveTrue(Integer studentClassId, Integer branchId, Sort sort);

    List<Student> findAllByStudentClassIdAndActiveTrue(Integer studentClassId);

    Page<Student> findAllByBranchIdAndActiveTrue(Pageable pageable, Integer id);

    List<Student> findAllByBranchIdAndActiveFalseOrderByAddedTimeAsc(Integer branchId, Sort pageable);

    List<Student> findAllByBranchIdAndActiveTrue(Integer branchId, Pageable pageable);

    Optional<Student> findByIdAndActiveTrue(Integer id);

    List<Student> findAllByIdInAndActiveTrue(List<Integer> id);

    Optional<Student> findByAccountNumberAndActiveTrue(String accountNumber);

    Optional<Student> findByPhoneNumberAndPassword(String phoneNumber, String password);

    List<Student> findAllByFirstNameContainingIgnoreCaseAndActiveTrue(String firstName);

    List<Student> findAllByLastNameContainingIgnoreCaseAndActiveTrue(String lastname);

    List<Student> findAllByDocNumberContainingIgnoreCaseAndActiveTrue(String docNumber);
}
