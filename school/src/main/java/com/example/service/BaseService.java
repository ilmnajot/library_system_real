package com.example.service;

import com.example.model.common.ApiResponse;

public interface BaseService <T ,R> {

    ApiResponse create(T t);

    ApiResponse getById(R r);

    ApiResponse update(T t);

    ApiResponse delete(R r);
}
