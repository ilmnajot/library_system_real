package com.example.repository;

import com.example.entity.Score;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ScoreRepository extends JpaRepository<Score, Integer> {

    Page<Score> findAllByTeacherIdAndSubjectLevelId(Integer teacherId, Integer subjectId, Pageable pageable);

    List<Score> findAllByTeacherIdAndSubjectLevelIdAndCreatedDateBetween(Integer teacherId, Integer subjectLevelId, LocalDateTime startWeek, LocalDateTime endWeek,Sort sort);

    Page<Score> findAllByJournalId(Integer journalId,Pageable pageable);
    List<Score> findAllByStudentIdAndCreatedDateBetween(Integer studentId, LocalDateTime createdDate, LocalDateTime createdDate2,Sort sort);

    Page<Score> findAllBySubjectLevelIdAndStudentId(Integer subjectLevelId, Integer studentId, Pageable pageable);

    List<Score> findAllByCreatedDateBetween(LocalDateTime createdDate, LocalDateTime createdDate2,Sort sort);


    Page<Score> findAllByStudentId(Integer studentId,Pageable pageable);

    Page<Score> findAllByStudentIdAndSubjectLevelId(Integer studentId, Integer subjectLevelId, PageRequest id);
}
