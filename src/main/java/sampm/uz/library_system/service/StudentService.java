package sampm.uz.library_system.service;

import sampm.uz.library_system.model.request.StudentRequest;
import sampm.uz.library_system.model.common.ApiResponse;

public interface StudentService {

    ApiResponse addStudent(StudentRequest request);
    ApiResponse getStudent(Long id);

    ApiResponse getStudents(int page, int size);
    ApiResponse getStudents();

    ApiResponse updateStudent(StudentRequest request, Long id);
    ApiResponse deleteStudent(Long id);

    ApiResponse getBookToStudent(Long bookId , Long studentId);

    ApiResponse getBooksByCategory(int page, int size, String category);

    ApiResponse getBooksByAuthor(int page, int size, Long author_id);
}
