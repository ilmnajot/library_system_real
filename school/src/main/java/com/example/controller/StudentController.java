package com.example.controller;

import com.example.annotations.CheckPermission;
import com.example.model.common.ApiResponse;
import com.example.model.request.FamilyLoginDto;
import com.example.model.request.StudentRequest;
import com.example.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/student")
public class StudentController {

    private final StudentService service;

    //    @CheckPermission("ADD_STUDENT")
    @PostMapping("/create")
    public ApiResponse create(@RequestBody StudentRequest studentRequest) {
        return service.create(studentRequest);
    }

    //    @CheckPermission("GET_STUDENT")
    @GetMapping("/getById/{id}")
    public ApiResponse getById(@PathVariable Integer id) {
        return service.getById(id);
    }

    //    @CheckPermission("EDIT_STUDENT")
    @PutMapping("/update")
    public ApiResponse update(@RequestBody StudentRequest studentRequest) {
        return service.update(studentRequest);
    }

    @PutMapping("/isActive/{id}")
    public ApiResponse isActiveStudent(@PathVariable Integer id) {
        return service.isActiveStudent(id);
    }

    //    @CheckPermission("DELETE_STUDENT")
    @DeleteMapping("/delete/{id}")
    public ApiResponse delete(@PathVariable Integer id) {
        return service.delete(id);
    }

    //    //    @CheckPermission("GET_STUDENT")
    @GetMapping("/getAll")
    public ApiResponse getAll(@RequestParam(name = "page", defaultValue = "0") int page,
                              @RequestParam(name = "size", defaultValue = "5") int size,
                              @RequestParam(name = "branchId") int branchId) {
        return service.getList(page, size, branchId);
    }

    //    @CheckPermission("GET_STUDENT")

    /*todo bu get yo'li ishlatilayotgani haqida malumot olishim kerak*/
    @GetMapping("/getAllByClassId/{classId}/{branchId}")
    public ApiResponse getAllByClassName(@PathVariable Integer classId, @PathVariable Integer branchId) {
        return service.getListByClassNumber(classId, branchId);
    }


    ////    @CheckPermission("GET_STUDENT")
    @GetMapping("/getAllNeActiveStudents/{branchId}")
    public ApiResponse getAllNeActiveStudents(@PathVariable Integer branchId) {
        return service.getAllNeActiveStudents(branchId);
    }

    @PostMapping("/studentLogin")
    public ApiResponse studentLogin(@RequestBody FamilyLoginDto studentLogin) {
        return service.studentLogIn(studentLogin);
    }

    //    @CheckPermission("GET_STUDENT")
    @GetMapping("/search")
    public HttpEntity<?> search(@RequestParam String name,
                                @RequestParam(name = "page", defaultValue = "0") int page,
                                @RequestParam(name = "size", defaultValue = "5") int size) {
        ApiResponse apiResponse = service.search(name, page, size);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @GetMapping("/getStudentFilter/{classId}")
    public HttpEntity<?> getStudentFilter(@PathVariable Integer classId,
                                          @RequestParam(name = "page", defaultValue = "0") int page,
                                          @RequestParam(name = "size", defaultValue = "5") int size,
                                          @RequestParam boolean isDebt) {
        ApiResponse apiResponse = service.getStudentFilter(classId, page, size,isDebt);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }



}
