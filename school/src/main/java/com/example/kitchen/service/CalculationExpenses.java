package com.example.kitchen.service;

import com.example.entity.Student;
import com.example.enums.Constants;
import com.example.kitchen.entity.DailyConsumedDrinks;
import com.example.kitchen.entity.DailyConsumedProducts;
import com.example.kitchen.entity.PurchasedProducts;
import com.example.kitchen.repository.DailyConsumedDrinksRepository;
import com.example.kitchen.repository.DailyConsumedProductsRepository;
import com.example.kitchen.repository.PurchasedDrinksRepository;
import com.example.kitchen.repository.PurchasedProductsRepository;
import com.example.model.common.ApiResponse;
import com.example.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CalculationExpenses {

    private final PurchasedDrinksRepository purchasedDrinksRepository;
    private final DailyConsumedDrinksRepository dailyConsumedDrinksRepository;
    private final DailyConsumedProductsRepository dailyConsumedProductsRepository;
    private final PurchasedProductsRepository purchasedProductsRepository;
    private final StudentRepository studentRepository;


    public ApiResponse getMonthlyExpenses(Integer branchId, int page, int size) {

        List<Student> allStudent = studentRepository
                .findAllByBranchIdAndActiveTrue(branchId, PageRequest.of(page, size));

        Page<DailyConsumedDrinks> allDrinks = dailyConsumedDrinksRepository
                .findAllByBranch_IdAndDeleteFalse(branchId, PageRequest.of(page, size));

        Page<DailyConsumedProducts> allProducts = dailyConsumedProductsRepository
                .findAllByBranchIdAndDeleteFalse(branchId, PageRequest.of(page, size));





        return new ApiResponse(Constants.SUCCESSFULLY, true );
    }

}
