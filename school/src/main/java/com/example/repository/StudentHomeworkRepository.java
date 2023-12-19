package com.example.repository;

import com.example.entity.StudentHomework;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface StudentHomeworkRepository extends JpaRepository<StudentHomework,Integer> {
  Optional<StudentHomework> findByDateAndLessonHourAndStudentClassIdAndActiveTrue(LocalDate date, int lessonHour, Integer studentClass_id);

  Optional<StudentHomework> findByIdAndActiveTrue(Integer integer);

  List<StudentHomework> findAllByActiveTrue(Sort sort);
}
