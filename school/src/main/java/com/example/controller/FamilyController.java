package com.example.controller;

import com.example.model.common.ApiResponse;
import com.example.model.request.FamilyLoginDto;
import com.example.model.request.FamilyRequest;
import com.example.service.FamilyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/family")
public class FamilyController {

    private final FamilyService familyService;

    @PostMapping("/create")
    public ApiResponse create(@RequestBody  FamilyRequest family) {
        return familyService.create(family);
    }

    @GetMapping("/getById/{id}")
    public ApiResponse getById(@PathVariable Integer id) {
        return familyService.getById(id);
    }


    @PutMapping("/update")
    public ApiResponse update(@RequestBody FamilyRequest family) {
        return familyService.update(family);
    }

    @DeleteMapping("/delete/{id}")
    public ApiResponse delete(@PathVariable Integer id) {
        return familyService.delete(id);
    }


    @GetMapping("/getAllActiveFamily")
    public ApiResponse getAllActiveClasses(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "5") int size,
            @RequestParam(name = "branchId") int branchId) {
        return familyService.getList(page, size,branchId);
    }

    @GetMapping("/loginFamily")
    public ApiResponse loginFamily(@RequestParam String phoneNumber,
                                   @RequestParam String password){
       return familyService.familyLogIn(phoneNumber, password);
    }
}
