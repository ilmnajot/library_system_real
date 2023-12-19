package com.example.model.request;

import com.example.entity.Salary;
import com.example.enums.ExpenseType;
import com.example.enums.PaymentType;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionHistoryRequest {

    private Integer id;

    private String moneyAmount;

    @NotBlank(message = "must be comment")
    private String comment;

    @Enumerated(EnumType.STRING)
    private ExpenseType expenseType;

    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;

    private boolean paidInFull;

    private String phoneNumber;

    private String  accountNumber;

    @Min(value = 1, message = "must be mainBalanceId")
    private Integer mainBalanceId;

    @Min(value = 1, message = "must be branchId")
    private Integer branchId;

}
