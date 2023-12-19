package com.example.service;

import com.example.entity.Branch;
import com.example.entity.Role;
import com.example.enums.Constants;
import com.example.enums.Permissions;
import com.example.exception.RecordAlreadyExistException;
import com.example.exception.RecordNotFoundException;
import com.example.model.common.ApiResponse;
import com.example.model.request.RoleRequestDto;
import com.example.model.response.RoleResponseList;
import com.example.repository.BranchRepository;
import com.example.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.enums.Constants.*;

@Service
@RequiredArgsConstructor
public class RoleService implements BaseService<RoleRequestDto, Integer> {

    private final ModelMapper modelMapper;
    private final RoleRepository roleRepository;
    private final BranchRepository branchRepository;

    @Override
    public ApiResponse create(RoleRequestDto requestDto) {
        Role role = modelMapper.map(requestDto, Role.class);
        setRole(requestDto, role);
        roleRepository.save(role);
        return new ApiResponse(SUCCESSFULLY, true, role);
    }

    private void setRole(RoleRequestDto requestDto, Role role) {
        if (roleRepository.findByNameAndActiveTrue(requestDto.getName()).isPresent()) {
            throw new RecordAlreadyExistException(Constants.ROLE_ALREADY_EXIST);
        }
        Role parentRole = roleRepository.findById(requestDto.getParentId())
                .orElseThrow(() -> new RecordNotFoundException(PARENT_ROLE_NOT_FOUND));
        Branch branch = branchRepository.findById(requestDto.getBranchId())
                .orElseThrow(() -> new RecordNotFoundException(BRANCH_NOT_FOUND));
        List<Permissions> permissions = requestDto.getPermissions();

        role.setActive(true);
        role.setParentRole(parentRole);
        role.setBranch(branch);
        role.setPermissions(permissions);
    }

    @Override
    public ApiResponse getById(Integer id) {
        Role role = roleRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new RecordNotFoundException(ROLE_NOT_FOUND));
        return new ApiResponse(SUCCESSFULLY, true, role);
    }

    public ApiResponse update(RoleRequestDto dto) {
        Role role = roleRepository.findById(dto.getId())
                .orElseThrow(() -> new RecordNotFoundException(ROLE_NOT_FOUND));
        setRole(dto, role);
        roleRepository.save(role);
        return new ApiResponse(SUCCESSFULLY, true, role);
    }

    @Override
    public ApiResponse delete(Integer id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(Constants.ROLE_NOT_AVAILABLE));
        role.setActive(false);
        roleRepository.save(role);
        return new ApiResponse(SUCCESSFULLY, true);
    }

    public ApiResponse getList(int size, int page, int branchId) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Role> roles = roleRepository.findAllByBranchIdAndActiveTrue(branchId, pageable);
        return new ApiResponse(new RoleResponseList(roles.getContent(), roles.getTotalElements(), roles.getTotalPages(), roles.getNumberOfElements()), true);
    }

    public ApiResponse getListByBranchId(Integer branchId) {
        List<Role> roles = roleRepository.findAllByBranchIdAndActiveTrue(branchId);
        return new ApiResponse(SUCCESSFULLY, true, roles);
    }
}
