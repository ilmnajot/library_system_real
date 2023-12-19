package com.example.service;

import com.example.entity.Branch;
import com.example.entity.MainBalance;
import com.example.enums.Constants;
import com.example.exception.RecordNotFoundException;
import com.example.model.common.ApiResponse;
import com.example.model.request.MainBalanceRequest;
import com.example.model.response.MainBalanceResponse;
import com.example.repository.BranchRepository;
import com.example.repository.MainBalanceRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MainBalanceService implements BaseService<MainBalanceRequest, Integer> {

    private final MainBalanceRepository mainBalanceRepository;
    private final BranchRepository branchRepository;
    private final ModelMapper modelMapper;

    @Override
    public ApiResponse create(MainBalanceRequest mainBalanceRequest) {
        MainBalance mainBalance = modelMapper.map(mainBalanceRequest, MainBalance.class);
        setMainBalance(mainBalanceRequest, mainBalance);
        mainBalanceRepository.save(mainBalance);
        MainBalanceResponse response = getMainBalanceResponse(mainBalance);
        return new ApiResponse(Constants.SUCCESSFULLY, true, response);
    }

    private void setMainBalance(MainBalanceRequest mainBalanceRequest, MainBalance mainBalance) {
        mainBalance.setActive(true);
        mainBalance.setDate(LocalDate.now());
        mainBalance.setBranch(getBranch(mainBalanceRequest.getBranchId()));
    }

    @Override
    public ApiResponse getById(Integer id) {
        MainBalance mainBalance = mainBalanceRepository
                .findByIdAndActiveTrue(id).orElseThrow(() -> new RecordNotFoundException(Constants.MAIN_BALANCE_NOT_FOUND));
        MainBalanceResponse response = getMainBalanceResponse(mainBalance);
        return new ApiResponse(Constants.SUCCESSFULLY, true, response);
    }


    public ApiResponse getByBranchId(Integer branchId) {
        List<MainBalance> all = mainBalanceRepository.findAllByBranch_IdAndActiveTrue(branchId, Sort.by(Sort.Direction.DESC, "id"));
        List<MainBalanceResponse> allResponse = new ArrayList<>();
        all.forEach(mainBalance -> {
            allResponse.add(getMainBalanceResponse(mainBalance));
        });
        return new ApiResponse(Constants.SUCCESSFULLY, true, allResponse);
    }

    @Override
    public ApiResponse update(MainBalanceRequest mainBalanceRequest) {
        getMainBalance(mainBalanceRequest.getId());
        MainBalance mainBalance = modelMapper.map(mainBalanceRequest, MainBalance.class);
        setMainBalance(mainBalanceRequest, mainBalance);
        mainBalance.setId(mainBalanceRequest.getId());
        mainBalanceRepository.save(mainBalance);
        MainBalanceResponse response = getMainBalanceResponse(mainBalance);
        return new ApiResponse(Constants.SUCCESSFULLY, true, response);
    }

    @Override
    public ApiResponse delete(Integer integer) {
        MainBalance mainBalance = getMainBalance(integer);
        mainBalance.setActive(false);
        mainBalanceRepository.save(mainBalance);
        MainBalanceResponse response = getMainBalanceResponse(mainBalance);
        return new ApiResponse(Constants.SUCCESSFULLY, true, response);
    }

    private MainBalanceResponse getMainBalanceResponse(MainBalance mainBalance) {
        MainBalanceResponse response = modelMapper.map(mainBalance, MainBalanceResponse.class);
//        MainBalanceResponse response = modelMapper.map(mainBalance, MainBalanceResponse.class);
        response.setDate(mainBalance.getDate().toString());
        return response;
    }

    private MainBalance getMainBalance(Integer integer) {
        return mainBalanceRepository.findByIdAndActiveTrue(integer)
                .orElseThrow(() -> new RecordNotFoundException(Constants.MAIN_BALANCE_NOT_FOUND));
    }

    private Branch getBranch(Integer branchId) {
        return branchRepository.findByIdAndDeleteFalse(branchId)
                .orElseThrow(() -> new RecordNotFoundException(Constants.BRANCH_NOT_FOUND));
    }
}
