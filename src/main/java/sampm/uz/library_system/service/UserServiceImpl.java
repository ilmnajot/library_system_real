package sampm.uz.library_system.service;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import sampm.uz.library_system.entity.Author;
import sampm.uz.library_system.entity.Book;
import sampm.uz.library_system.entity.Student;
import sampm.uz.library_system.entity.User;
import sampm.uz.library_system.enums.Status;
import sampm.uz.library_system.model.common.ApiResponse;
import sampm.uz.library_system.model.request.BookRequest;
import sampm.uz.library_system.model.request.StudentRequest;
import sampm.uz.library_system.model.request.UserRequest;
import sampm.uz.library_system.model.response.BookResponse;
import sampm.uz.library_system.model.response.StudentResponse;
import sampm.uz.library_system.model.response.UserResponse;
import sampm.uz.library_system.repository.BookRepository;
import sampm.uz.library_system.repository.StudentRepository;
import sampm.uz.library_system.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final BookRepository bookRepository;
    private final StudentRepository studentRepository;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, BookRepository bookRepository, StudentRepository studentRepository) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.bookRepository = bookRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    public ApiResponse addBook(BookRequest request) {
        Optional<Book> optionalBook = bookRepository.findBookByBookNameAndAvailableTrue(request.getBookName());
        if (optionalBook.isPresent()) {
            return new ApiResponse("this book has already been added", false, "here is book you wanted to add: " + optionalBook.get());
        }
        Book book = new Book();
        book.setBookName(request.getBookName());
        book.setIsbn(request.getIsbn());
        book.setDescription(request.getDescription());
        book.setCategory(request.getCategory());
        book.setAvailable(true);
        book.setAuthor(
                Author
                        .builder()
                        .fullName(request.getAuthor().getFullName())
                        .email(request.getAuthor().getEmail())
                        .city(request.getAuthor().getCity())
                        .build());
        Book savedBook = bookRepository.save(book);
        BookResponse bookResponse = modelMapper.map(savedBook, BookResponse.class);
        return new ApiResponse("this book has been added", false, bookResponse);
    }

//    @Override
//    public ApiResponse addBookByUser(UserRequest request) {
//        return null;
//    }

    @Override
    public ApiResponse addBookByBookRequest(BookRequest request) {
        Optional<Book> optionalBook = bookRepository.findBookByBookNameAndAvailableTrue(request.getBookName());
        if (optionalBook.isPresent()) {
            return new ApiResponse("this book has already been added", false, "here is book you wanted to add: " + optionalBook.get());
        }
        Student student = new Student();
        Book book = new Book();
        book.setBookName(student.getFullName());
        book.setAvailable(true);
        return new ApiResponse("this book has been added", false, modelMapper.map(bookRepository.save(book), BookResponse.class));
    }

//    @Override
//    public ApiResponse addBook(UserRequest request) {
//        return null;
//    }

    @Override
    public ApiResponse getBook(Long id) {
        Optional<Book> optionalBook = bookRepository.findBookByIdAndAvailableTrue(id);
        if (optionalBook.isPresent()) {
            optionalBook.get().setAvailable(true); //need to ask if really need here setAvailable(true); todo
            BookResponse bookResponse = modelMapper.map(bookRepository.save(optionalBook.get()), BookResponse.class);
            return new ApiResponse("here is the book you are looking for: ", true, bookResponse);
        }
        return new ApiResponse("no such book", false);
    }

    @Override
    public ApiResponse getAllAvailableBook(int page, int size) {
        Page<Book> books = bookRepository.findAllByAvailableTrue(PageRequest.of(page, size));
        return new ApiResponse("list of books", true, books.map(book -> modelMapper.map(book, BookResponse.class)));
    }

    @Override
    public ApiResponse getAllNotAvailableBook(int page, int size) {
        Page<Book> books = bookRepository.findAllByAvailableTrue(PageRequest.of(page, size));
        return new ApiResponse("all deleted books", true, books.map(book -> modelMapper.map(book, BookResponse.class)));
    }

    @Override
    public ApiResponse deleteBook(Long id) {
        Optional<Book> optionalBook = bookRepository.findBookByIdAndAvailableTrue(id);
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
//            book.setDeleted(false);
            book.setAvailable(false);
            return new ApiResponse("deleted book", true, modelMapper.map(bookRepository.save(book), BookResponse.class));
        }
        return new ApiResponse("there is no such book", false);
    }

    @Override
    public ApiResponse updateBook(BookRequest request, Long id) {
        Optional<Book> optionalBook = bookRepository.findBookByIdAndAvailableTrue(id);
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            book.setId(id);
            book.setBookName(request.getBookName());
            book.setIsbn(request.getIsbn());
            book.setDescription(request.getDescription());
            book.setCategory(request.getCategory());
//            book.setAvailable(request.getAuthor();
            book.setAuthor(
                    Author
                            .builder()
                            .fullName(request.getAuthor().getFullName())
                            .email(request.getAuthor().getEmail())
                            .city(request.getAuthor().getCity())
                            .build());
            return new ApiResponse("success", true, modelMapper.map(bookRepository.save(book), BookResponse.class));
        }
        return new ApiResponse("there is not available", false);
    }

    @Override
    public ApiResponse getStudent(Long id) {
        Optional<Student> optionalUser = studentRepository.findStudentByIdAndAvailableTrue(id);
        if (optionalUser.isPresent()) {
            Student student = optionalUser.get();
            student.setAvailable(true);
            Student savedStudent = studentRepository.save(student);
            StudentResponse userResponse = modelMapper.map(savedStudent, StudentResponse.class);
            return new ApiResponse("here is the user", true, userResponse);
        }
        return new ApiResponse("there is not available user", false);
    }

    @Override
    public ApiResponse getAllAvailableStudent(int page, int size) {
        Page<Student> students = studentRepository.findAllByAvailableTrue(PageRequest.of(page, size));
        return new ApiResponse("success", true, students.map(student -> modelMapper.map(student, StudentResponse.class)));
    }

    @Override
    public ApiResponse getAllNotAvailableStudent(int page, int size) {
        Page<Student> students = studentRepository.findAllByAvailableFalse(PageRequest.of(page, size));
        return new ApiResponse("success", true, students.map(student -> modelMapper.map(student, StudentResponse.class)));
    }

    @Override
    public ApiResponse deleteStudent(Long id) {
        Optional<Student> studentOptional = studentRepository.findStudentByIdAndAvailableTrue(id);
        if (studentOptional.isPresent()) {
            if (studentOptional.get().getStatus().equals(Status.NOT_IN_DEBT)) {
                Student student = studentOptional.get();
                student.setAvailable(false);
                Student savedStudent = studentRepository.save(student);
                StudentResponse studentResponse = modelMapper.map(savedStudent, StudentResponse.class);
                return new ApiResponse("success", true, studentResponse);
            }
            return new ApiResponse("this student has to return the books he or she took back", false);
        }
        return new ApiResponse("there is no student with this id: " + id, false);
    }

    @Override
    public ApiResponse updateStudent(StudentRequest request, Long id) {
        Optional<Student> studentOptional = studentRepository.findStudentByIdAndAvailableTrue(id);
        if (studentOptional.isPresent()) {
            Student student = studentOptional.get();
            student.setFullName(request.getFullName());
            student.setEmail(request.getEmail());
            student.setStudentGrade(request.getStudentGrade());
            student.setSchoolName(request.getSchoolName());
            student.setStatus(request.getStatus());
//            student.setBooks(List.of());
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
        user.setAvailable(true);
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
        Optional<Student> optionalStudent = studentRepository.findStudentByEmailAndAvailableTrue(request.getEmail());
        if (optionalStudent.isPresent()) {
            return new ApiResponse("student with email " + optionalStudent.get().getEmail() + " is already available", false, optionalStudent.get());
        }
        Student student = new Student();
        student.setFullName(request.getFullName());
        student.setEmail(request.getEmail());
        student.setStudentGrade(request.getStudentGrade());
        student.setSchoolName(request.getSchoolName());
        student.setStatus(request.getStatus());
        student.setAvailable(true);
        Student savedStudent = studentRepository.save(student);
        StudentResponse studentResponse = modelMapper.map(savedStudent, StudentResponse.class);
        return new ApiResponse("success", true, studentResponse);
    }
}
