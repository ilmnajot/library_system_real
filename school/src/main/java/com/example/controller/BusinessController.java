package com.example.controller;

import com.example.entity.Business;
import com.example.model.common.ApiResponse;
import com.example.model.request.BusinessRequest;
import com.example.service.BusinessService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/business")
public class BusinessController {

    private final BusinessService businessService;

    @PostMapping("/create")
    public ApiResponse create(@RequestBody BusinessRequest business) {
        return businessService.create(business);
    }

    @GetMapping("/getById/{id}")
    public ApiResponse getById(@PathVariable Integer id) {
        return businessService.getById(id);
    }

    @PutMapping("/update")
    public ApiResponse update(@RequestBody BusinessRequest business) {
        return businessService.update(business);
    }

    @DeleteMapping("/delete/{id}")
    public ApiResponse delete(@PathVariable Integer id) {
        return businessService.delete(id);
    }

    @GetMapping("/activateBusiness/{id}")
    public ApiResponse activateBusiness(@PathVariable Integer id) {
        return businessService.activate(id);
    }

    @GetMapping("/deActivateBusiness/{id}")
    public ApiResponse deActivateBusiness(@PathVariable Integer id) {
        return businessService.deActivate(id);
    }


    @GetMapping("/getAll")
    public ApiResponse getAllActiveBusiness(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "5") int size
    ) {
        return businessService.getAll(page, size);
    }
}
