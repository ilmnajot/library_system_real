package com.example.repository;

import com.example.entity.SubjectLevel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SubjectLevelRepository extends JpaRepository<SubjectLevel, Integer> {
    Optional<SubjectLevel> findByIdAndActiveTrue(Integer subjectId);

    Optional<SubjectLevel> findByBranchIdAndSubjectIdAndLevelIdAndActiveTrue(Integer branch_id, Integer subject_id, Integer level_id);

    Page<SubjectLevel> findAllByBranch_IdAndActiveTrue(Integer id, Pageable pageable);

    List<SubjectLevel> findAllByBranchIdAndActiveTrue(Integer id);
}
