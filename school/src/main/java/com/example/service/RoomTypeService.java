package com.example.service;

import com.example.entity.Branch;
import com.example.entity.RoomType;
import com.example.exception.RecordAlreadyExistException;
import com.example.exception.RecordNotFoundException;
import com.example.model.common.ApiResponse;
import com.example.model.request.RoomTypeRequest;
import com.example.repository.BranchRepository;
import com.example.repository.RoomTypeRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

import static com.example.enums.Constants.*;

@Service
@RequiredArgsConstructor
public class RoomTypeService implements BaseService<RoomTypeRequest, Integer> {

    private final RoomTypeRepository roomTypeRepository;
    private final BranchRepository branchRepository;
    private final ModelMapper modelMapper;

    @Override
    public ApiResponse create(RoomTypeRequest newRoomType) {
        RoomType roomType = modelMapper.map(newRoomType, RoomType.class);
        setRoomType(newRoomType, roomType);
        roomTypeRepository.save(roomType);
        return new ApiResponse(SUCCESSFULLY, true, roomType);
    }

    private void setRoomType(RoomTypeRequest newRoomType, RoomType roomType) {
        if (roomTypeRepository.existsByBranchIdAndName(newRoomType.getBranchId(), newRoomType.getName())) {
            throw new RecordAlreadyExistException(ROOM_TYPE_ALREADY_EXIST);
        }
        Branch branch = branchRepository.findById(newRoomType.getBranchId())
                .orElseThrow(() -> new RecordNotFoundException(BRANCH_NOT_FOUND));

        roomType.setActive(true);
        roomType.setBranch(branch);
    }

    @Override
    public ApiResponse getById(Integer integer) {
        RoomType roomType = roomTypeRepository.findById(integer)
                .orElseThrow(() -> new RecordNotFoundException(ROOM_TYPE_NOT_FOUND));
        return new ApiResponse(SUCCESSFULLY, true, roomType);
    }

    @Override
    public ApiResponse update(RoomTypeRequest roomTypeRequest) {
        RoomType roomType = roomTypeRepository.findById(roomTypeRequest.getId())
                .orElseThrow(() -> new RecordNotFoundException(ROOM_TYPE_NOT_FOUND));
        setRoomType(roomTypeRequest, roomType);
        roomTypeRepository.save(roomType);
        return new ApiResponse(SUCCESSFULLY, true, roomType);
    }

    @Override
    public ApiResponse delete(Integer integer) {
        RoomType roomType = roomTypeRepository.findById(integer)
                .orElseThrow(() -> new RecordNotFoundException(ROOM_TYPE_NOT_FOUND));
        roomType.setActive(false);
        roomTypeRepository.save(roomType);
        return new ApiResponse(DELETED, true, roomType);
    }

    public ApiResponse getListRoomsByBranchId(Integer id) {
        List<RoomType> allByBranchId = roomTypeRepository.findAllByBranchIdAndActiveTrue(id, Sort.by(Sort.Direction.DESC, "id"));
        return new ApiResponse(allByBranchId, true);
    }
}
