package sampm.uz.library_system.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sampm.uz.library_system.entity.StudentBook;
import sampm.uz.library_system.model.common.ApiResponse;
import sampm.uz.library_system.service.StudentService;
import sampm.uz.library_system.service.UserService;

import static sampm.uz.library_system.utils.Constants.*;


@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;
    private final UserService userService;

    public StudentController(StudentService studentService, UserService userService) {
        this.studentService = studentService;
        this.userService = userService;
    }

    @GetMapping(GET_BOOK)
    public HttpEntity<ApiResponse> getBook(@PathVariable Long id) {
        ApiResponse book = userService.getBook(id);
        return ResponseEntity.status(book.isSuccess() ? 200 : 409).body(book);
    }

    @GetMapping(SEARCH)
    public HttpEntity<ApiResponse> getBookByName(@RequestParam String bookName) {
        ApiResponse book = userService.getBookByBookName(bookName);
        return ResponseEntity.status(book.isSuccess() ? 200 : 409).body(book);
    }

    @GetMapping(GET_ALL_BOOK)
    public HttpEntity<ApiResponse> getAllBook(@RequestParam(name = "page", defaultValue = "0") int page,
                                              @RequestParam(name = "size", defaultValue = "9") int size) {
        ApiResponse allBook = userService.findAll(page, size);
        return allBook != null
                ? ResponseEntity.ok(allBook)
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @GetMapping(GET_ALL_AVAILABLE_BOOK)
    public HttpEntity<ApiResponse> getAllAvailableBook(@RequestParam(name = "page", defaultValue = "0") int page,
                                                       @RequestParam(name = "size", defaultValue = "9") int size) {
        ApiResponse allDeletedBook = userService.getAllAvailableBook(page, size);
        return allDeletedBook != null
                ? ResponseEntity.status(HttpStatus.FOUND).body(allDeletedBook)
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @GetMapping(GET_ALL_NOT_AVAILABLE_BOOK)
    public HttpEntity<ApiResponse> getAllNotAvailableBook(@RequestParam(name = "page", defaultValue = "0") int page,
                                                          @RequestParam(name = "size", defaultValue = "9") int size) {
        ApiResponse allDeletedBook = userService.getAllNotAvailableBook(page, size);
        return allDeletedBook != null
                ? ResponseEntity.status(HttpStatus.ACCEPTED).body(allDeletedBook)
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @GetMapping("/get_my_book/{studentId}")
    public HttpEntity<ApiResponse> getMyBook(@RequestParam(name = "page", defaultValue = "0") int page,
                                             @RequestParam(name = "size", defaultValue = "9") int size,
                                             @PathVariable(name = "studentId") Long studentId) {
        ApiResponse allMyBook = userService.getAllMyBook(page, size, studentId);
        return allMyBook != null
                ? ResponseEntity.status(HttpStatus.ACCEPTED).body(allMyBook)
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); }
}
