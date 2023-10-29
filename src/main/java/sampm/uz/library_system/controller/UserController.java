package sampm.uz.library_system.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sampm.uz.library_system.model.common.ApiResponse;
import sampm.uz.library_system.model.request.BookRequest;
import sampm.uz.library_system.model.request.StudentRequest;
import sampm.uz.library_system.model.request.UserRequest;
import sampm.uz.library_system.service.StudentService;
import sampm.uz.library_system.service.UserService;
import static sampm.uz.library_system.utils.Constants.*;
import static sampm.uz.library_system.utils.Constants.GET_ALL_BOOK;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final StudentService studentService;
    private final UserService userService;

    public UserController(StudentService studentService, UserService userService) {
        this.studentService = studentService;
        this.userService = userService;
    }

    @PostMapping(REGISTER_USER)
    public HttpEntity<ApiResponse> registerUser(@RequestBody UserRequest request) {
        ApiResponse user = userService.registerUser(request);
        return user != null
                ? ResponseEntity.ok(user)
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @PostMapping(VERIFY_USER)
    public HttpEntity<ApiResponse> verifyUser(@RequestBody String emailCode) {
        ApiResponse user = userService.verifyUser(emailCode);
        return user != null
                ? ResponseEntity.ok(user)
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @PostMapping(LOGIN)
    public HttpEntity<ApiResponse> login(@RequestBody UserRequest request) {
        ApiResponse user = userService.login(request);
        return user != null
                ? ResponseEntity.ok(user)
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }


    @PostMapping(ADD_STUDENT)
    public HttpEntity<ApiResponse> addStudent(@RequestBody StudentRequest request) {
        ApiResponse student = userService.addStudent(request);
        return student != null
                ? ResponseEntity.ok(student)
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @GetMapping(GET_STUDENT)
    public HttpEntity<ApiResponse> getStudent(@PathVariable Long id) {
        ApiResponse student = userService.getStudent(id);
        return student != null
                ? ResponseEntity.ok(student)
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @GetMapping(GET_ALL_STUDENT)
    public ResponseEntity<ApiResponse> getAllStudent(@RequestParam(name = "page", defaultValue = "0") int page,
                                                     @RequestParam(name = "size", defaultValue = "9") int size) {
        ApiResponse students = userService.getAllAvailableStudent(page, size);
        return students != null
                ? ResponseEntity.ok(students)
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @GetMapping(GET_ALL_NON_EXIST_STUDENT)
    public ResponseEntity<ApiResponse> getAllNonExistStudent(@RequestParam(name = "page", defaultValue = "0") int page,
                                                             @RequestParam(name = "size", defaultValue = "9") int size) {
        ApiResponse students = userService.getAllGraduatedStudents(page, size);
        return students != null
                ? ResponseEntity.ok(students)
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @DeleteMapping(DELETE_STUDENT)
    public HttpEntity<ApiResponse> deleteStudent(@PathVariable Long id) {
        ApiResponse student = userService.deleteStudent(id);
        return student != null
                ? ResponseEntity.ok(student)
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @PutMapping(UPDATE_STUDENT)
    public HttpEntity<ApiResponse> updateStudent(@RequestBody StudentRequest request, @PathVariable Long id) {
        ApiResponse student = userService.updateStudent(request, id);
        return student != null
                ? ResponseEntity.ok(student)
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @PostMapping(BOOK_TO_STUDENT)
    public HttpEntity<ApiResponse> bookToStudent(@PathVariable Long bookId, @PathVariable Long studentId) {
        ApiResponse bookToStudent = userService.getBookToStudent(bookId, studentId);
        return bookToStudent != null
                ? ResponseEntity.ok(bookToStudent)
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @PostMapping(ADD_BOOK)
    public HttpEntity<ApiResponse> addBook(@RequestBody BookRequest request) {
        ApiResponse book = userService.addBook(request);
        return book != null
                ? ResponseEntity.ok(book)
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
    @PostMapping(INCREASE_BOOK)
    public HttpEntity<ApiResponse> incrementBook(
            @PathVariable(name = "bookId") Long bookId,
            @RequestParam(name = "increment_amount") int increment_amount){
        ApiResponse apiResponse = userService.incrementBook(bookId, increment_amount);
        return apiResponse != null
                ? ResponseEntity.ok(apiResponse)
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
    @PostMapping(DECREASE_BOOK)
    public HttpEntity<ApiResponse> decrementBook(
            @PathVariable(name = "bookId") Long bookId,
            @RequestParam(name = "decrement_amount") int decrement_amount){
        ApiResponse apiResponse = userService.decrementBook(bookId, decrement_amount);
        return apiResponse != null
                ? ResponseEntity.ok(apiResponse)
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @GetMapping(GET_BOOK)
    public HttpEntity<ApiResponse> getBook(@PathVariable Long id) {
        ApiResponse book = userService.getBook(id);
        return book != null
                ? ResponseEntity.ok(book)
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @GetMapping(GET_ALL_BOOK)
    public HttpEntity<ApiResponse> getAllBook(@RequestParam(name = "page", defaultValue = "0") int page,
                                              @RequestParam(name = "size", defaultValue = "9") int size) {
        ApiResponse allBook = userService.getAllAvailableBook(page, size);
        return allBook != null
                ? ResponseEntity.ok(allBook)
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @PutMapping(UPDATE_BOOK)
    public HttpEntity<ApiResponse> updateBook(
            @RequestBody BookRequest request,
            @PathVariable Long id) {
        ApiResponse book = userService.updateBook(request, id);
        return book != null
                ? ResponseEntity.ok(book)
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @DeleteMapping(DELETE_BOOK)
    public HttpEntity<ApiResponse> deleteBook(@PathVariable Long id) {
        ApiResponse book = userService.deleteBook(id);
        return book != null
                ? ResponseEntity.ok(book)
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @GetMapping(GET_ALL_DELETED_BOOK)
    public HttpEntity<ApiResponse> getAllDeletedBook(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "9") int size) {
        ApiResponse allDeletedBook = userService.getAllNotAvailableBook(page, size);
        return allDeletedBook != null
                ? ResponseEntity.ok(allDeletedBook)
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @GetMapping(GET_ALL_AVAILABLE_BOOK)
    public HttpEntity<ApiResponse> getAllAvailableBook(@RequestParam(name = "page", defaultValue = "0") int page,
                                                       @RequestParam(name = "size", defaultValue = "9") int size) {
        ApiResponse allDeletedBook = userService.getAllAvailableBook(page, size);
        return allDeletedBook != null
                ? ResponseEntity.ok(allDeletedBook)
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @GetMapping(GET_ALL_NOT_AVAILABLE_BOOK)
    public HttpEntity<ApiResponse> getAllNotAvailableBook(@RequestParam(name = "page", defaultValue = "0") int page,
                                                          @RequestParam(name = "size", defaultValue = "9") int size) {
        ApiResponse allDeletedBook = userService.getAllNotAvailableBook(page, size);
        return allDeletedBook != null
                ? ResponseEntity.ok(allDeletedBook)
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}