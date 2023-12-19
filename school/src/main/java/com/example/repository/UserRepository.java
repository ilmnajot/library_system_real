package com.example.repository;


import com.example.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {


    Optional<User> findByPhoneNumberAndBlockedFalse(String phoneNumber);


    Page<User> findAllByBranch_IdAndBlockedFalse(Integer branch_id, Pageable pageable);

    Page<User> findAllByBlockedFalse(Pageable pageable);

    List<User> findAllByBranch_IdAndBlockedFalse(Integer branch_id, Sort sort);

    Optional<User> findByPhoneNumberAndVerificationCode(String phoneNumber, Integer verificationCode);

    boolean existsByPhoneNumberAndBlockedFalse(String phoneNumber);

    Optional<User> findByIdAndBlockedFalse(Integer teacherId);
}
