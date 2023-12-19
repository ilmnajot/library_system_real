package com.example.kitchen.service;

import com.example.entity.Branch;
import com.example.enums.Constants;
import com.example.exception.RecordAlreadyExistException;
import com.example.exception.RecordNotFoundException;
import com.example.kitchen.entity.Warehouse;
import com.example.kitchen.model.response.WarehouseResponse;
import com.example.kitchen.model.request.WareHouseRequest;
import com.example.kitchen.repository.WareHouseRepository;
import com.example.model.common.ApiResponse;
import com.example.repository.BranchRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WareHouseService implements BaseService<WareHouseRequest, Integer> {

    private final WareHouseRepository wareHouseRepository;
    private final BranchRepository branchRepository;
    private final ModelMapper modelMapper;


    @Override
    public ApiResponse create(WareHouseRequest wareHouseRequest) {
        if (wareHouseRepository.findByNameAndActiveTrueAndBranchId(wareHouseRequest.getName(), wareHouseRequest.getBranchId()).isPresent()) {
            throw new RecordAlreadyExistException(Constants.WAREHOUSE_ALREADY_EXIST);
        }
        Warehouse warehouse = modelMapper.map(wareHouseRequest, Warehouse.class);
        setWarehouse(wareHouseRequest, warehouse);
        wareHouseRepository.save(warehouse);
        return new ApiResponse(Constants.SUCCESSFULLY, true, warehouse);
    }

    private void setWarehouse(WareHouseRequest wareHouseRequest, Warehouse warehouse) {
        Branch branch = branchRepository.findByIdAndDeleteFalse(wareHouseRequest.getBranchId())
                .orElseThrow(() -> new RecordNotFoundException(Constants.BRANCH_NOT_FOUND));
        warehouse.setBranch(branch);
        warehouse.setActive(true);
    }

    @Override
    public ApiResponse getById(Integer integer) {
        Warehouse warehouse = getWarehouseId(integer);
        return new ApiResponse(Constants.SUCCESSFULLY, true, warehouse);
    }

    public ApiResponse getAllByBranchId(Integer branchId, int page, int size) {
        Page<Warehouse> all = wareHouseRepository
                .findAllByActiveTrueAndBranchId(branchId, PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id")));

        WarehouseResponse response = getResponse(all);
        return new ApiResponse(Constants.SUCCESSFULLY, true, response);
    }

    public ApiResponse getAll(int page, int size) {
        Page<Warehouse> all = wareHouseRepository
                .findAllByActiveTrue(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id")));

        WarehouseResponse response = getResponse(all);
        return new ApiResponse(Constants.SUCCESSFULLY, true, response);
    }

    private WarehouseResponse getResponse(Page<Warehouse> all) {
        return new WarehouseResponse(
                all.getContent(),
                all.getTotalElements(),
                all.getTotalPages(),
                all.getNumber());
    }

    @Override
    public ApiResponse update(WareHouseRequest wareHouseRequest) {
        if (wareHouseRepository.findByIdAndActiveTrue(wareHouseRequest.getId()).isEmpty()) {
            throw new RecordNotFoundException(Constants.WAREHOUSE_NOT_FOUND);
        }
        Warehouse warehouse = modelMapper.map(wareHouseRequest, Warehouse.class);
        setWarehouse(wareHouseRequest, warehouse);
        warehouse.setId(wareHouseRequest.getId());
        wareHouseRepository.save(warehouse);
        return new ApiResponse(Constants.SUCCESSFULLY, true, warehouse);
    }

    @Override
    public ApiResponse delete(Integer integer) {
        Warehouse warehouse = getWarehouseId(integer);
        wareHouseRepository.delete(warehouse);
        return new ApiResponse(Constants.DELETED, true, warehouse);
    }

    private Warehouse getWarehouseId(Integer id) {
        return wareHouseRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new RecordNotFoundException(Constants.WAREHOUSE_NOT_FOUND));
    }
}
