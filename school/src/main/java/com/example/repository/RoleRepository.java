package com.example.repository;

import com.example.entity.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByNameAndActiveTrue(String name);
    Optional<Role> findByIdAndActiveTrue(Integer id);

    Page<Role> findAllByBranchIdAndActiveTrue(Integer branchId, Pageable pageable);
    List<Role> findAllByBranchIdAndActiveTrue(Integer branchId);
}
