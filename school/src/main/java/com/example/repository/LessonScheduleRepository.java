package com.example.repository;

import com.example.entity.LessonSchedule;
import com.example.entity.StudentClass;
import com.example.enums.WeekDays;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface LessonScheduleRepository extends JpaRepository<LessonSchedule, Integer> {
   Optional<LessonSchedule> findByStudentClassIdAndLessonHourAndBranchIdAndDate(Integer studentClass_id, int lessonHour, Integer branch_id, WeekDays date);
   Optional<LessonSchedule> findByStudentClassIdAndLessonHourAndBranchIdAndDateAndActiveTrue(Integer studentClass_id, int lessonHour, Integer branch_id, WeekDays date);
   Optional<LessonSchedule> findByRoomIdAndLessonHourAndBranchIdAndDateAndActiveTrue(Integer roomId, int lessonHour, Integer branch_id, WeekDays date);
   Optional<LessonSchedule> findByTeacherIdAndLessonHourAndBranchIdAndDateAndActiveTrue(Integer teacherId, int lessonHour, Integer branch_id, WeekDays date);
   Optional<LessonSchedule> findByIdAndActiveTrue(Integer id);
   Page<LessonSchedule> findByBranchIdAndActiveTrue(Integer id, Pageable pageable);
   Page<LessonSchedule> findByStudentClassLevel_IdAndActiveTrue(Integer id, Pageable pageable);
   Page<LessonSchedule> findByTeacherIdAndActiveTrue(Integer id, Pageable pageable);
}
