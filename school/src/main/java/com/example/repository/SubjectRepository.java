package com.example.repository;

import com.example.entity.Subject;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SubjectRepository extends JpaRepository<Subject,Integer> {
    Optional<Subject> findByName(String name);
    Optional<Subject> findByIdAndActiveTrue(Integer id);

    List<Subject> findAllByBranchIdAndActiveTrue(Integer branchId, Sort sort);
}
