package com.example.repository;


import com.example.entity.AttachmentContent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AttachmentContentRepository extends JpaRepository<AttachmentContent, Integer> {
    Optional<AttachmentContent> findByAttachmentId(Integer id);
}
