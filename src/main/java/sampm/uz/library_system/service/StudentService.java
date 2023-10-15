package sampm.uz.library_system.service;

import sampm.uz.library_system.model.request.StudentRequest;
import sampm.uz.library_system.model.common.ApiResponse;

public interface StudentService {

    ApiResponse addStudent(StudentRequest request);
    ApiResponse getStudent(Long id);

    ApiResponse getStudents(int page, int size);

    ApiResponse updateStudent(StudentRequest request, Long id);
    ApiResponse deleteStudent(Long id);
}
