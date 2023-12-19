package com.example.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Topic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @OneToMany
    private List<Attachment> lessonFiles;

    @ElementCollection
    private List<String> useFullLinks;

    
    private LocalDateTime creationDate;

    @ManyToOne
    private SubjectLevel subjectLevel;
}
