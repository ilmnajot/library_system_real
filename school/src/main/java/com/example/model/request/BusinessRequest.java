package com.example.model.request;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusinessRequest {
    private Integer id;

    @Column(nullable = false)
    private String name;

    private String description;

    private String phoneNumber;
}
