package com.example.repository;

import com.example.entity.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AttachmentRepository extends JpaRepository<Attachment, Integer> {

    Optional<Attachment> findByName(String name);
    Optional<Attachment> findAllById(Integer id);
}
