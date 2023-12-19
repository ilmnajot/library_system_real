package com.example.controller;

import com.example.model.common.ApiResponse;
import com.example.model.request.TariffDto;
import com.example.service.TariffService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tariff/")
public class TariffController {

    private final TariffService tariffService;

    @PostMapping("/save")
//    @PreAuthorize("hasAnyRole('SUPER_ADMIN') or hasAnyAuthority('SAVE_TARIFF')")
    public ApiResponse save(@RequestBody TariffDto tariffDto) {
        return tariffService.create(tariffDto);
    }

    @GetMapping("/deActivate/{id}")
    public ApiResponse deActivate(@PathVariable Integer id) {
        return tariffService.deActivate(id);
    }

    @GetMapping("/activate/{id}")
    public ApiResponse activate(@PathVariable Integer id) {
        return tariffService.activate(id);
    }

    @GetMapping("/getTariffById/{id}")
    public ApiResponse getTariffByID(@PathVariable Integer id) {
        return tariffService.getById(id);
    }

    @GetMapping("/getTariffListForAdmin")
    public ApiResponse getTariffListForAdmin() {
        return tariffService.getTariffListForAdmin();
    }

    @GetMapping("/getTariffListForUser")
    public ApiResponse getTariffListForUser() {
        return tariffService.getTariffListForUser();
    }

    @PutMapping("/update")
//    @PreAuthorize("hasAnyRole('SUPER_ADMIN') or hasAnyAuthority('UPDATE_TARIFF')")
    public ApiResponse update(@RequestBody TariffDto tariffDto) {
        return tariffService.update(tariffDto);
    }

    @DeleteMapping("/remove/{id}")
//    @PreAuthorize("hasAnyRole('SUPER_ADMIN') or hasAnyAuthority('REMOVE_TARIFF')")
    public ApiResponse remove(@PathVariable Integer id) {
        return tariffService.delete(id);
    }
}
