package com.example.kitchen.service;

import com.example.model.common.ApiResponse;

public interface BaseService <T,I>{
    ApiResponse create(T t);
    ApiResponse getById(I i);
    ApiResponse update(T t);
    ApiResponse delete(I i);
}
