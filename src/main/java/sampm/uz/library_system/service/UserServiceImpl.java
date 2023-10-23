package sampm.uz.library_system.service;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sampm.uz.library_system.entity.*;
import sampm.uz.library_system.enums.Status;
import sampm.uz.library_system.exception.BookException;
import sampm.uz.library_system.model.common.ApiResponse;
import sampm.uz.library_system.model.request.BookRequest;
import sampm.uz.library_system.model.request.StudentRequest;
import sampm.uz.library_system.model.request.UserRequest;
import sampm.uz.library_system.model.response.BookResponse;
import sampm.uz.library_system.model.response.StudentResponse;
import sampm.uz.library_system.model.response.UserResponse;
import sampm.uz.library_system.repository.AuthorRepository;
import sampm.uz.library_system.repository.BookRepository;
import sampm.uz.library_system.repository.StudentRepository;
import sampm.uz.library_system.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.hibernate.cfg.AvailableSettings.USER;
import static sampm.uz.library_system.enums.Role.STUDENT;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final BookRepository bookRepository;
    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthorRepository authorRepository;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, BookRepository bookRepository, StudentRepository studentRepository, PasswordEncoder passwordEncoder, AuthorRepository authorRepository) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.bookRepository = bookRepository;
        this.studentRepository = studentRepository;
        this.passwordEncoder = passwordEncoder;
        this.authorRepository = authorRepository;
    }


    @Override
    public ApiResponse addBook(BookRequest request) {
        Optional<Book> optionalBook = bookRepository.findBookByIsbn(request.getIsbn());
        if (optionalBook.isPresent()) { // TODO: 10/16/2023
            return new ApiResponse("this book has already been added", false, "here is book you wanted to add: " + optionalBook.get());
        }
        Book book = new Book();
        book.setBookName(request.getBookName());
        book.setIsbn(request.getIsbn());
        book.setDescription(request.getDescription());
        book.setCategory(request.getCategory());
//        book.setAvailable(true);
        /*book.setAuthor(
                Author
                        .builder()
                        .fullName(request.getAuthor().getFullName())
                        .email(request.getAuthor().getEmail())
                        .city(request.getAuthor().getCity())
                        .build());*/
//        book.setStudent(
//                Student
//                        .builder()
//                        .fullName(request.getStudent().getFullName())
//                        .email(request.getStudent().getEmail())
//                        .studentGrade(request.getStudent().getStudentGrade())
//                        .schoolName(request.getStudent().getSchoolName())
//                        .status(request.getStudent().getStatus())
//                        .build());
        Book savedBook = bookRepository.save(book);
        BookResponse bookResponse = modelMapper.map(savedBook, BookResponse.class);
        return new ApiResponse("this book has been added", false, bookResponse);
    }

    @Override
    public ApiResponse addBookByUser(UserRequest request) {
        return null;
    }

    @Override
    public ApiResponse addBookByUserRequest(BookRequest request) {
        Optional<Book> optionalBook = bookRepository.findBookByIsbn(request.getIsbn());
        if (optionalBook.isPresent()) {
            return new ApiResponse("this book has already been added", false, "here is book you wanted to add: " + optionalBook.get());
        }
        Student student = new Student();
        Book book = new Book();
        book.setBookName(student.getFullName());
        //book.setAvailable(true);
        return new ApiResponse("this book has been added", false, modelMapper.map(bookRepository.save(book), BookResponse.class));
    }


    @Override
    public ApiResponse getBook(Long id) {
        Book book = getBookById(id);
        Book savedBook = bookRepository.save(book);
        BookResponse bookResponse = modelMapper.map(savedBook, BookResponse.class);
        return new ApiResponse("here is the book you want to get: ", true, bookResponse);
    }
    public Book getBookById(Long id) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isPresent()) {
            return optionalBook.get();
        }
        throw new BookException("there is no book with id: " + id);
    }

    @Override
    public ApiResponse getAllAvailableBook(int page, int size) { // TODO: 10/16/2023 change... Exception Handler
        Page<Book> books = bookRepository.findAllByAvailableTrue(PageRequest.of(page, size));
        return new ApiResponse("list of books", true, books.map(book -> modelMapper.map(book, BookResponse.class)));
    }

    @Override
    public ApiResponse getAllNotAvailableBook(int page, int size) {
        Page<List<Book>> listPage = bookRepository.findAllByCount(Sort.by("id"), PageRequest.of(page, size));
        if
        return new ApiResponse("all deleted books", true, listPage.map(book -> modelMapper.map(book, BookResponse.class)));
    }

    @Override
    public ApiResponse deleteBook(Long id) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isPresent()) {
            bookRepository.deleteById(id);
            return new ApiResponse("the book has been deleted", true);
        }
        throw new BookException("there is no such book");
    }
    public Author getAuthorById(Long authorId){
        Optional<Author> optionalAuthor = authorRepository.findById(authorId);
        if (optionalAuthor.isPresent()) {
            return optionalAuthor.get();
        }
        throw new BookException("there is no such author");
    }

    @Override
    public ApiResponse updateBook(BookRequest request, Long id) { // TODO: 10/23/2023
        Optional<Book> optionalBook = bookRepository.findBookByIsbn(request.getIsbn());
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            book.setId(id);
            book.setBookName(request.getBookName());
            book.setIsbn(request.getIsbn());
            book.setDescription(request.getDescription());
            book.setCategory(request.getCategory());
            book.setCount(request.getCount());
            book.setAuthor(getAuthorById(request.getAuthorId()));
            return new ApiResponse("success", true, modelMapper.map(bookRepository.save(book), BookResponse.class));
        }
        throw new BookException("there is not available");
    }

    @Override
    public ApiResponse getStudent(Long id) {
        Optional<Student> optionalUser = studentRepository.findStudentByIdAndGraduatedFalse(id);
        if (optionalUser.isPresent()) {
            Student savedStudent = studentRepository.save(optionalUser.get());
            StudentResponse userResponse = modelMapper.map(savedStudent, StudentResponse.class);
            return new ApiResponse("here is the user", true, userResponse);
        }
        throw new BookException("there is not available user");
    }

    @Override
    public ApiResponse getAllAvailableStudent(int page, int size) {
        Page<Student> students = studentRepository.findAllByGraduatedFalse(Sort.by("id"), PageRequest.of(page, size));
        return new ApiResponse("success", true, students.map(student -> modelMapper.map(student, StudentResponse.class)));
    }

    @Override
    public ApiResponse getAllGraduatedStudents(int page, int size) {
        Page<Student> students = studentRepository.findAllByGraduatedFalse(Sort.by("id"), PageRequest.of(page, size));
        return new ApiResponse("success", true, students.map(student -> modelMapper.map(student, StudentResponse.class)));
    }

    @Override
    public ApiResponse deleteStudent(Long id) {
        Optional<Student> studentOptional = studentRepository.findStudentByIdAndGraduatedFalse(id);
        if (studentOptional.isPresent()) {
            if (studentOptional.get().getStatus().equals(Status.NOT_IN_DEBT)) {
                Student student = studentOptional.get();
                studentRepository.deleteById(id);
                Student savedStudent = studentRepository.save(student);
                StudentResponse studentResponse = modelMapper.map(savedStudent, StudentResponse.class);
                return new ApiResponse("success", true, studentResponse);
            }
            throw new BookException("this student has to return the books he or she took back");
        }
        throw new BookException("there is no student with this id: " + id);
    }

    @Override
    public ApiResponse updateStudent(StudentRequest request, Long id) {
        Optional<Student> studentOptional = studentRepository.findStudentByEmailAndGraduatedFalse(request.getEmail());
        if (studentOptional.isPresent()) {
            Student student = studentOptional.get();
            student.setFullName(request.getFullName());
            student.setEmail(request.getEmail());
            student.setPassword(passwordEncoder.encode(request.getPassword()));
            student.setStudentGrade(request.getStudentGrade());
            student.setSchoolName(request.getSchoolName());
            student.setGraduated(request.isGraduated());
            student.setStatus(request.getStatus());
            Student savedStudent = studentRepository.save(student);
            StudentResponse studentResponse = modelMapper.map(savedStudent, StudentResponse.class);
            return new ApiResponse("success", true, studentResponse);
        }

        return new ApiResponse("failed to update", false);
    }

    @Override
    public ApiResponse registerUser(UserRequest request) {
        Optional<User> optionalUser = userRepository.findUserByEmail(request.getEmail());
        if (optionalUser.isPresent()) {
            return new ApiResponse("user already registered with this email: " + request.getEmail(), false, optionalUser.get());
        }
        User user = new User();
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setWorkPlace(request.getWorkPlace());
        user.setPosition(request.getPosition());
        user.setSchoolName(request.getSchoolName());
        user.setRole(Role.builder().name(USER).build());
        user.setDeleted(false);
        User savedUser = userRepository.save(user);
        UserResponse userResponse = modelMapper.map(savedUser, UserResponse.class);
        return new ApiResponse("success", true, userResponse);
    }

    @Override
    public ApiResponse verifyUser(String request) {

        return null;
    }

    @Override
    public ApiResponse login(UserRequest request) {

        return null;
    }

    @Override
    public ApiResponse addStudent(StudentRequest request) {
        Optional<Student> optionalStudent = studentRepository.findStudentByEmailAndGraduatedFalse(request.getEmail());
        if (optionalStudent.isPresent()) {
            throw new BookException("student with email " + optionalStudent.get().getEmail() + " is already available");
        }
        Student student = new Student();
        student.setFullName(request.getFullName());
        student.setEmail(request.getEmail());
        student.setStudentGrade(request.getStudentGrade());
        student.setSchoolName(request.getSchoolName());
        student.setStatus(request.getStatus());
        student.setRole(Role.builder().name(STUDENT.name()).build());
        student.setGraduated(false);
        Student savedStudent = studentRepository.save(student);
        StudentResponse studentResponse = modelMapper.map(savedStudent, StudentResponse.class);
        return new ApiResponse("success", true, studentResponse);
    }

    @Override
    public ApiResponse getBookByBookName(String bookName) {
        List<Book> bookList = bookRepository.findBookByBookName(bookName);
        if (bookList!=null){
        Stream<BookResponse> responseStream = bookList.stream().map(book -> modelMapper.map(book, BookResponse.class));
        return new ApiResponse("success", true, responseStream);
    }
        throw new BookException("the book not found");

    }
}
