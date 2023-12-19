package com.example.repository;

import com.example.entity.RoomType;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomTypeRepository extends JpaRepository<RoomType, Integer> {
    boolean existsByBranchIdAndName(Integer branch_id, String name);

    List<RoomType> findAllByBranchIdAndActiveTrue(Integer branch_id, Sort sort);
}
