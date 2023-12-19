package com.example.model.request;

import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JournalRequest {

    private Integer id;

    private Integer studentClassId;

    private Integer branchId;
}
