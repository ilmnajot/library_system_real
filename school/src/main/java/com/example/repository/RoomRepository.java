package com.example.repository;

import com.example.entity.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Integer> {

    boolean existsByBranchIdAndRoomNumber(Integer branch_id, Integer roomNumber);

    Page<Room> findAllByBranchIdAndActiveTrue(Integer branch_id,Pageable pageable);
    List<Room> findAllByBranchIdAndActiveTrue(Integer branch_id, Sort sort);

    Optional<Room> findByIdAndActiveTrue(Integer roomId);
}
