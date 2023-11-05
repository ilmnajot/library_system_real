package sampm.uz.library_system.service;

import sampm.uz.library_system.model.request.StudentRequest;
import sampm.uz.library_system.model.common.ApiResponse;
import sampm.uz.library_system.model.response.StudentResponse;

import java.util.List;

public interface StudentService {

    ApiResponse addStudent(StudentRequest request);
    ApiResponse getStudent(Long id);

    ApiResponse getStudents(int page, int size);
    List<StudentResponse> getStudents();

    ApiResponse updateStudent(StudentRequest request, Long id);
    ApiResponse deleteStudent(Long id);

    ApiResponse getBookToStudent(Long bookId , Long studentId);
}
