package com.example.repository;

import com.example.entity.Achievement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AchievementRepository extends JpaRepository<Achievement,Integer> {

    Page<Achievement> findAllByUserId(Integer userId, Pageable pageable);

}
