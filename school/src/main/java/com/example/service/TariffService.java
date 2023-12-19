package com.example.service;

import com.example.entity.Tariff;
import com.example.enums.Constants;
import com.example.enums.Permissions;
import com.example.exception.RecordNotFoundException;
import com.example.model.common.ApiResponse;
import com.example.model.request.TariffDto;
import com.example.repository.TariffRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.example.enums.Constants.*;

@Service
@RequiredArgsConstructor
public class TariffService implements BaseService<TariffDto, Integer> {

    private final TariffRepository repository;
    private final ModelMapper modelMapper;


    @Override
    public ApiResponse create(TariffDto tariffDto) {
        Tariff tariff = modelMapper.map(tariffDto, Tariff.class);
        setTariff(tariffDto, tariff);
        repository.save(tariff);
        return new ApiResponse(Constants.SUCCESSFULLY, true, tariff);
    }

    private void setTariff(TariffDto tariffDto, Tariff tariff) {
        tariff.setActive(true);
        tariff.setDelete(false);
        List<Permissions> permissionsList = tariffDto.getPermissionsList();
        tariff.setPermissions(permissionsList);
    }

    @Override
    public ApiResponse getById(Integer id) {
        return new ApiResponse(Constants.SUCCESSFULLY, true, checkById(id));
    }

    @Override
    public ApiResponse update(TariffDto tariffDto) {
        checkById(tariffDto.getId());
        Tariff tariff = modelMapper.map(tariffDto, Tariff.class);
        setTariff(tariffDto, tariff);
        tariff.setId(tariffDto.getId());
        repository.save(tariff);
        return new ApiResponse(Constants.SUCCESSFULLY, true, tariff);
    }

    @Override
    public ApiResponse delete(Integer id) {
        Tariff tariff = checkById(id);
        tariff.setDelete(true);
        repository.save(tariff);
        return new ApiResponse(Constants.DELETED, true, tariff);
    }

    public ApiResponse deActivate(Integer id) {
        Tariff tariff = repository.findByIdAndDeleteFalse(id)
                .orElseThrow(() -> new RecordNotFoundException(TARIFF_NOT_FOUND));
        tariff.setActive(false);
        repository.save(tariff);
        return new ApiResponse(DEACTIVATED, true, tariff);
    }

    public ApiResponse activate(Integer id) {
        Tariff tariff = repository.findByIdAndDeleteFalse(id)
                .orElseThrow(() -> new RecordNotFoundException(TARIFF_NOT_FOUND));
        tariff.setActive(true);
        repository.save(tariff);
        return new ApiResponse(ACTIVATED, true, tariff);
    }

    public ApiResponse getTariffListForAdmin() {
        List<Tariff> tariffList = repository
                .findAllByDelete(false, Sort.by(Sort.Direction.DESC, "id"));
        tariffList.sort(Comparator.comparing(Tariff::getPrice));
        return new ApiResponse(SUCCESSFULLY, true, tariffList);
    }


    public ApiResponse getTariffListForUser() {
        List<Tariff> tariffList = repository
                .findAllByActiveAndDelete(true, false, Sort.by(Sort.Direction.DESC, "id"));
        tariffList.sort(Comparator.comparing(Tariff::getPrice));
        return new ApiResponse(SUCCESSFULLY, true, tariffList);
    }

    private Tariff checkById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(Constants.TARIFF_NOT_FOUND));
    }
}
