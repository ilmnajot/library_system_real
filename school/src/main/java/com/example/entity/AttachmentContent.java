package com.example.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;


@Entity
@Data
@NoArgsConstructor
public class AttachmentContent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private byte[] mainContent;

    @OneToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Attachment attachment;

    public AttachmentContent(byte[] mainContent, Attachment attachment) {
        this.mainContent = mainContent;
        this.attachment = attachment;
    }


}
