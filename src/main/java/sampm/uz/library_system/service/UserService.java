package sampm.uz.library_system.service;

import sampm.uz.library_system.model.common.ApiResponse;
import sampm.uz.library_system.model.request.BookRequest;
import sampm.uz.library_system.model.request.StudentRequest;
import sampm.uz.library_system.model.request.UserRequest;

 public  interface UserService {
     ApiResponse addBookByUserRequest(BookRequest request);
     ApiResponse addBookByUser(UserRequest request);
     ApiResponse addBook(BookRequest request);
     ApiResponse getBook(Long id);
     ApiResponse deleteBook(Long id);
     ApiResponse updateBook(BookRequest request,Long id);
     ApiResponse getStudent(Long id);
     ApiResponse getAllAvailableStudent(int page, int size);
     ApiResponse getAllGraduatedStudents(int page, int size);
     ApiResponse deleteStudent(Long id);
     ApiResponse updateStudent(StudentRequest request, Long id);

     ApiResponse registerUser(UserRequest request);

    ApiResponse verifyUser(String request);

    ApiResponse login(UserRequest request);

     ApiResponse getAllAvailableBook(int page, int size);

     ApiResponse getAllNotAvailableBook(int page, int size);

     ApiResponse addStudent(StudentRequest request);

     ApiResponse getBookByBookName(String bookName);

     ApiResponse incrementBook(Long bookId, int incrementAmount);

     ApiResponse decrementBook(Long bookId, int decrementAmount);

     ApiResponse getBookToStudent(Long bookId, Long studentId);
 }
