package com.example.model.request;

import com.example.enums.Lifetime;
import com.example.enums.Permissions;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TariffDto {

    private Integer id;
    private String name;

    private String description;

    private int branchAmount;

    private long productAmount;

    private int employeeAmount;

    private long tradeAmount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Lifetime lifetime;

    private int testDay;

    private int interval;

    private double price;

    private double discount;

    private boolean active;

    private boolean delete;

    private List<Permissions> permissionsList;
}
