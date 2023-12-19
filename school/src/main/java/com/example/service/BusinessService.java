package com.example.service;

import com.example.entity.Business;
import com.example.exception.RecordAlreadyExistException;
import com.example.exception.RecordNotFoundException;
import com.example.model.common.ApiResponse;
import com.example.model.request.BusinessRequest;
import com.example.model.response.BusinessResponseListForAdmin;
import com.example.repository.BusinessRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import static com.example.enums.Constants.*;

@RequiredArgsConstructor
@Service
public class BusinessService implements BaseService<BusinessRequest, Integer> {

    private final BusinessRepository businessRepository;
    private final ModelMapper modelMapper;

    @Override
    public ApiResponse create(BusinessRequest businessRequest) {
        if (businessRepository.existsByName(businessRequest.getName())) {
            throw new RecordAlreadyExistException(BUSINESS_NAME_ALREADY_EXIST);
        }
        Business business = modelMapper.map(businessRequest, Business.class);
        business.setActive(true);
        business.setDelete(false);
        businessRepository.save(business);
        return new ApiResponse(SUCCESSFULLY, true, business);
    }

    @Override
    public ApiResponse getById(Integer integer) {
        Business business = businessRepository.findById(integer)
                .orElseThrow(() -> new RecordNotFoundException(BUSINESS_NOT_FOUND));
        return new ApiResponse(business, true);
    }

    @Override
    public ApiResponse update(BusinessRequest newBusiness) {
        businessRepository.findById(newBusiness.getId())
                .orElseThrow(() -> new RecordNotFoundException(BUSINESS_NOT_FOUND));
        Business business = modelMapper.map(newBusiness, Business.class);
        business.setId(newBusiness.getId());
        business.setActive(true);
        business.setDelete(false);
        businessRepository.save(business);
        return new ApiResponse(SUCCESSFULLY, true,business);
    }

    @Override
    public ApiResponse delete(Integer integer) {
        Business business = businessRepository.findById(integer)
                .orElseThrow(() -> new RecordNotFoundException(BUSINESS_NOT_FOUND));
        business.setDelete(true);
        businessRepository.save(business);
        return new ApiResponse(DELETED, true,business);
    }

    public ApiResponse getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Business> all = businessRepository.findAllByDeleteFalse(pageable);
        return new ApiResponse(new BusinessResponseListForAdmin(
                all.getContent(), all.getTotalElements(), all.getTotalPages(), all.getNumber()), true);
    }

    public ApiResponse deActivate(Integer integer) {
        Business business = businessRepository.findById(integer)
                .orElseThrow(() -> new RecordNotFoundException(BUSINESS_NOT_FOUND));
        business.setActive(false);
        businessRepository.save(business);
        return new ApiResponse(DEACTIVATED, true);
    }

    public ApiResponse activate(Integer integer) {
        Business business = businessRepository.findById(integer)
                .orElseThrow(() -> new RecordNotFoundException(BUSINESS_NOT_FOUND));
        business.setActive(true);
        businessRepository.save(business);
        return new ApiResponse(ACTIVATED, true);
    }
}
