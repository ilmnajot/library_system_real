package com.example.controller;

import com.example.model.common.ApiResponse;
import com.example.model.request.RoleRequestDto;
import com.example.service.RoleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/role")
public class RoleController {

    private final RoleService roleService;

    @PostMapping("/save")
//    @PreAuthorize("hasAuthority('ROLE_ACCESS' or 'SUPER_ADMIN')")
    public ApiResponse create(@RequestBody @Valid RoleRequestDto requestDto) {
        return roleService.create(requestDto);
    }

    @PutMapping("/update")
//    @PreAuthorize("hasAuthority('ROLE_ACCESS' or 'SUPER_ADMIN')")
    public ApiResponse update(@RequestBody @Valid RoleRequestDto requestDto) {
        return roleService.update(requestDto);
    }

    @GetMapping("/getRoleByID/{id}")
    public ApiResponse getRoleByID(@PathVariable Integer id) {
        return roleService.getById(id);
    }

    @GetMapping("/getList")
    public ApiResponse getList(
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "branchId") int branchId
    ) {
        return roleService.getList(size,page,branchId);
    }

    @GetMapping("/getListByBranchId")
    public ApiResponse getListByBranchId(
            @RequestParam(name = "branchId") Integer branchId
    ) {
        return roleService.getListByBranchId(branchId);
    }

    @DeleteMapping("/delete/{id}")
//    @PreAuthorize("hasAuthority('ROLE_ACCESS' or 'SUPER_ADMIN')")
    public ApiResponse remove(@PathVariable Integer id) {
        return roleService.delete(id);
    }
}
