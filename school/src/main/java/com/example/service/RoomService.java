package com.example.service;

import com.example.entity.Branch;
import com.example.entity.Room;
import com.example.entity.RoomType;
import com.example.exception.RecordNotFoundException;
import com.example.model.common.ApiResponse;
import com.example.model.request.RoomRequestDto;
import com.example.model.response.RoomResponsePage;
import com.example.repository.BranchRepository;
import com.example.repository.RoomRepository;
import com.example.repository.RoomTypeRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.enums.Constants.*;

@Service
@RequiredArgsConstructor
public class RoomService implements BaseService<RoomRequestDto, Integer> {

    private final ModelMapper modelMapper;
    private final RoomRepository roomRepository;
    private final BranchRepository branchRepository;
    private final RoomTypeRepository roomTypeRepository;

    @Override
    public ApiResponse create(RoomRequestDto roomRequestDto) {
        Room room = modelMapper.map(roomRequestDto, Room.class);
        setRoom(roomRequestDto, room);
        roomRepository.save(room);
        return new ApiResponse(SUCCESSFULLY, true);
    }

    private void setRoom(RoomRequestDto roomRequestDto, Room room) {
        if (roomRepository.existsByBranchIdAndRoomNumber(roomRequestDto.getBranchId(), roomRequestDto.getRoomNumber())) {
            throw new RecordNotFoundException(ROOM_NUMBER_ALREADY_EXIST);
        }
        Branch branch = branchRepository.findById(roomRequestDto.getBranchId())
                .orElseThrow(() -> new RecordNotFoundException(BRANCH_NOT_FOUND));
        RoomType roomType = roomTypeRepository.findById(roomRequestDto.getRoomTypeId())
                .orElseThrow(() -> new RecordNotFoundException(ROOM_TYPE_NOT_FOUND));

        room.setActive(true);
        room.setRoomType(roomType);
        room.setBranch(branch);
    }

    @Override
    public ApiResponse getById(Integer integer) {
        Room room = roomRepository.findById(integer)
                .orElseThrow(() -> new RecordNotFoundException(ROOM_NOT_FOUND));
        return new ApiResponse(SUCCESSFULLY, true, room);
    }

    @Override
    public ApiResponse update(RoomRequestDto roomRequestDto) {
        Room room = roomRepository.findById(roomRequestDto.getRoomId())
                .orElseThrow(() -> new RecordNotFoundException(ROOM_NOT_FOUND));
        setRoom(roomRequestDto, room);
        roomRepository.save(room);
        return new ApiResponse(SUCCESSFULLY, true, room);
    }

    @Override
    public ApiResponse delete(Integer integer) {
        Room room = roomRepository.findById(integer)
                .orElseThrow(() -> new RecordNotFoundException(ROOM_NOT_FOUND));
        room.setActive(false);
        roomRepository.save(room);
        return new ApiResponse(DELETED, true, room);
    }

    public ApiResponse getRoomListByBranchId(int page, int size, Integer branchId) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Room> all = roomRepository.findAllByBranchIdAndActiveTrue(branchId, pageable);
        RoomResponsePage roomResponsePage = new RoomResponsePage(
                all.getContent(), all.getTotalElements(), all.getTotalPages(), all.getNumber());
        return new ApiResponse(roomResponsePage, true);
    }

    public ApiResponse getRoomListByBranchId(Integer branchId) {
        List<Room> all = roomRepository.findAllByBranchIdAndActiveTrue(
                branchId, Sort.by(Sort.Direction.DESC, "id"));
        return new ApiResponse(SUCCESSFULLY, true, all);
    }
}