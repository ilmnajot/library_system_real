package sampm.uz.library_system.controller;
import jakarta.validation.Valid;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import sampm.uz.library_system.entity.User;
import sampm.uz.library_system.model.common
        .ApiResponse;
import sampm.uz.library_system.model.request.BookRequest;
import sampm.uz.library_system.model.request.StudentRequest;
import sampm.uz.library_system.model.request.UserRequest;
import sampm.uz.library_system.model.response.StudentResponse;
import sampm.uz.library_system.service.StudentService;
import sampm.uz.library_system.service.UserService;

import java.util.List;

import static sampm.uz.library_system.utils.Constants.*;
import static sampm.uz.library_system.utils.Constants.GET_ALL_BOOK;

//@RestController
@RequestMapping("/api/users")
@Controller
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
//    @GetMapping("/register")
//    public String showRegistrationForm(Model model){
//        model.addAttribute("student", new StudentRequest());
//        return "registrationForm";
//    }
//    @PostMapping("/register")
//    public String registerStudent(@ModelAttribute(name = "student") StudentRequest student){
//        userService.addStudent(student);
//        return "redirect:/registration-success";
//    }
//    @GetMapping("/login")
//    public String loginStudent(@ModelAttribute(name = "student") StudentRequest student){
//        userService.addStudent(student);
//        return "redirect:/loginForm";
//    }

    /**
     * student register
     * @return "redirect:/login"
     */
    @GetMapping("/register-student")
    public ModelAndView showStudentRegistrationForm(){
//        model.addAttribute("student", new StudentRequest());
        ModelAndView mav = new ModelAndView("student-registration-form"); //html oladi
        mav.addObject("student", new StudentRequest());
        return mav;
    }
    @PostMapping("/register-student")
    public String processStudentRegistration(@ModelAttribute(name = "student") StudentRequest student){
        userService.addStudent(student);
        return "redirect:/student-registration-form";
    }
    @GetMapping("/login")
    public ModelAndView showLoginPage(){
        ModelAndView mav = new ModelAndView("student-login-form");
        mav.addObject("student", new StudentRequest());
        return mav;
    }

    /**
     * teacher registration
     * @return teacher registration
     */
    @GetMapping("/register-teacher")
    public ModelAndView show1TeacherRegistrationForm(){
//        model.addAttribute("student", new StudentRequest());
        ModelAndView mav = new ModelAndView("teacher-registration-form"); //html oladi
        mav.addObject("user", new User());
        return mav;
    }
    @PostMapping("/register-teacher")
    public String processTeacherRegistration(@ModelAttribute(name = "user") UserRequest teacher){
        userService.registerUser(teacher);
        return "redirect:/teacher-registration-form";
    }
    @GetMapping("/loginTeacher")
    public ModelAndView showTeacherLoginPage(){
        ModelAndView mav = new ModelAndView("teacher-login-form");
        mav.addObject("user", new UserRequest());
        return mav;
    }

    @PostMapping("/verifyEmail")
    public String verifyEmail(@ModelAttribute(name = "verifyEmail") UserRequest student){
        userService.verifyEmail(student);
        return "redirect:/loginTeacher";
    }


    @GetMapping(GET_STUDENT)
    public HttpEntity<ApiResponse> getStudent(@PathVariable Long id) {
        ApiResponse student = userService.getStudent(id);
        return student != null
                ? ResponseEntity.ok(student)
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @GetMapping("student-list")
    public ModelAndView studentList() {
        List<StudentResponse> students = studentService.getStudents();
        ModelAndView mav = new ModelAndView("student-list"); //html oladi
        mav.addObject("students", students);
        return mav;
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
    public HttpEntity<ApiResponse> updateStudent(@RequestBody StudentRequest request, @PathVariable(name = "studentId") Long studentId) {
        ApiResponse student = userService.updateStudent(request, studentId);
        return student != null
                ? ResponseEntity.ok(student)
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
    @PutMapping(GRADUATE_STUDENT)
    public HttpEntity<ApiResponse> graduateTrue(@RequestBody StudentRequest request, @PathVariable(name = "studentId") Long studentId) {
        ApiResponse student = userService.graduateStudentTrue(request, studentId);
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

    @PostMapping(GET_SOME_BOOKS)
    public HttpEntity<ApiResponse> getBooksToStudent(
            @PathVariable(name = "bookId") Long bookId,
            @PathVariable(name = "studentId") Long studentId,
            @RequestParam(name = "amount") int amount) {
        ApiResponse booksToStudent = userService.getBooksToStudent(bookId, studentId, amount);
        return booksToStudent != null
                ? ResponseEntity.ok(booksToStudent)
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

    }

    @PostMapping(RETURN_BOOK)
    public HttpEntity<ApiResponse> returnBook(@PathVariable Long studentId,@PathVariable Long bookId) {
        ApiResponse apiResponse = userService.returnBook(studentId, bookId);
        return apiResponse != null
                ? ResponseEntity.ok(apiResponse)
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }


    @PostMapping(ADD_BOOK)
    public HttpEntity<ApiResponse> addBook(@Valid @RequestBody BookRequest request) {
        ApiResponse book = userService.addBook(request);
        return book != null
                ? ResponseEntity.ok(book)
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @PostMapping(INCREASE_BOOK)
    public HttpEntity<ApiResponse> incrementBook(
            @PathVariable(name = "bookId") Long bookId,
            @RequestParam(name = "increment_amount") int increment_amount) {
        ApiResponse apiResponse = userService.incrementBook(bookId, increment_amount);
        return apiResponse != null
                ? ResponseEntity.ok(apiResponse)
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @PostMapping(DECREASE_BOOK)
    public HttpEntity<ApiResponse> decrementBook(
            @PathVariable(name = "bookId") Long bookId,
            @RequestParam(name = "decrement_amount") int decrement_amount) {
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