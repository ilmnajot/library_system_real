package sampm.uz.library_system.service;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sampm.uz.library_system.entity.*;
import sampm.uz.library_system.enums.SchoolName;
import sampm.uz.library_system.enums.Status;
import sampm.uz.library_system.exception.BaseException;
import sampm.uz.library_system.exception.BookException;
import sampm.uz.library_system.exception.StudentException;
import sampm.uz.library_system.exception.TeacherException;
import sampm.uz.library_system.model.common.ApiResponse;
import sampm.uz.library_system.model.request.BookRequest;
import sampm.uz.library_system.model.request.StudentRequest;
import sampm.uz.library_system.model.request.UserRequest;
import sampm.uz.library_system.model.response.*;
import sampm.uz.library_system.repository.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.hibernate.cfg.AvailableSettings.USER;
import static sampm.uz.library_system.enums.Status.NOT_IN_DEBT;
import static sampm.uz.library_system.enums.Status.NOT_TAKE_BOOK;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final BookRepository bookRepository;
    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthorRepository authorRepository;
    private final StudentBookRepository studentBookRepository;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, BookRepository bookRepository, StudentRepository studentRepository, PasswordEncoder passwordEncoder, AuthorRepository authorRepository, StudentBookRepository studentBookRepositort, StudentBookRepository studentBookRepository) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.bookRepository = bookRepository;
        this.studentRepository = studentRepository;
        this.passwordEncoder = passwordEncoder;
        this.authorRepository = authorRepository;
        this.studentBookRepository = studentBookRepository;
    }


    @Override
    public ApiResponse addBook(BookRequest request) {
        Optional<Book> optionalBook = bookRepository.findBookByIsbn(request.getIsbn());
        if (optionalBook.isPresent()) {
            throw new BookException("this book has already been added:" + optionalBook.get());
        }
        if (!existAuthorById(request.getAuthorId())) {
            throw new BookException("author is not registered");
        }
        Book book = new Book();
        book.setBookName(request.getBookName());
        book.setIsbn(request.getIsbn());
        book.setDescription(request.getDescription());
        book.setCategory(request.getCategory());
        book.setAuthorId(request.getAuthorId());
        book.setCount(request.getCount());
        Book savedBook = bookRepository.save(book);
        BookResponse bookResponse = modelMapper.map(savedBook, BookResponse.class);
        return new ApiResponse("this book has been added", true, bookResponse);
    }

    public boolean existAuthorById(Long id) {
        return authorRepository.existsById(id);
    }

    @Override
    public ApiResponse incrementBook(Long bookId, int incrementAmount) {
        Optional<Book> optionalBook = bookRepository.findById(bookId);
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            book.setCount(book.getCount() + incrementAmount);
            Book savedBook = bookRepository.save(book);
            BookResponse bookResponse = modelMapper.map(savedBook, BookResponse.class);
            return new ApiResponse("this book has been incremented", true, bookResponse);
        }
        throw new BookException("there is no book with id: " + bookId);
    }

    @Override
    public ApiResponse decrementBook(Long bookId, int decrementAmount) {
        Optional<Book> optionalBook = bookRepository.findById(bookId);
        if (optionalBook.isEmpty()) {
            throw new BookException("there is no book with id: " + bookId);
        }
        Book book = optionalBook.get();
        if (book.getCount() - decrementAmount >= 0) {
            book.setCount(book.getCount() - decrementAmount);
            Book savedBook = bookRepository.save(book);
            BookResponse bookResponse = modelMapper.map(savedBook, BookResponse.class);
            return new ApiResponse("this book has been incremented", true, bookResponse);
        }
        throw new BaseException("you reach limit of book and only there are: " + book.getCount() + " existing", HttpStatus.BANDWIDTH_LIMIT_EXCEEDED);

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
        return new ApiResponse("this book has been added", false, modelMapper.map(bookRepository.save(book), BookResponse.class));
    }


    @Override
    public ApiResponse getBook(Long id) {
        Book book = getBookById(id);
        BookResponseById bookResponseById = modelMapper.map(book, BookResponseById.class);
        return new ApiResponse("here is the book you want to get: ", true, bookResponseById);
    }

    @Override
    public ApiResponse getBookToStudent(Long bookId, Long studentId) {
        if (!studentRepository.existsByIdAndGraduatedFalse(studentId)) {
            throw new StudentException("student not found with id " + studentId);
        }
        if (!bookRepository.existsById(bookId)) {
            throw new BookException("book not found with id " + bookId);
        }
        if (bookRepository.existsByIdAndCountGreaterThan(bookId, 0)) {
            StudentBook studentBook = new StudentBook();
            studentBook.setStudent_id(studentId);
            studentBook.setBook_id(bookId);
            studentBook.setAmount(studentBook.getAmount() + 1);
            Optional<Book> optionalBook = bookRepository.findById(bookId);
            Book book1 = optionalBook.get();
            Student student = studentRepository.findById(studentId).get();
            student.setNumberOfBooks(student.getNumberOfBooks() + 1);
            book1.setCount(book1.getCount() - 1);
            bookRepository.save(book1);
            StudentBook savedBookToStudent = studentBookRepository.save(studentBook);
            StudentBookResponse studentBookResponse = modelMapper.map(savedBookToStudent, StudentBookResponse.class);
            return new ApiResponse("successfully the book registered to the: " + studentId, true, studentBookResponse);
        }
        throw new BookException("there is no book to take");
    }

    @Override
    public ApiResponse getBooksToStudent(Long bookId, Long studentId, int amount) {
        if (!studentRepository.existsById(studentId)) {
            throw new StudentException("student not found with id: " + studentId);
        }
        if (!bookRepository.existsById(bookId)) {
            throw new BookException("there no book with id: " + bookId);
        }
        if (bookRepository.existsByIdAndCountLessThan(bookId, 1)) {
            throw new BookException("book is not available");
        }
        StudentBook studentBook = new StudentBook();
        Book book = bookRepository.findById(bookId).get();
        if (book.getCount() - amount >= 0) {
            studentBook.setAmount(amount);
            studentBook.setStudent_id(studentId);
            studentBook.setBook_id(bookId);
            book.setCount(book.getCount() - amount);
            bookRepository.save(book);
            StudentBook savedStudentBook = studentBookRepository.save(studentBook);
            StudentBookResponse mapped = modelMapper.map(savedStudentBook, StudentBookResponse.class);
            return new ApiResponse("congratulations: you have taken: " + studentBook.getAmount() + " all together but only " + book.getCount() + " left these type of book", true, mapped);
        }
        throw new BookException("only number of books left: " + book.getCount());
    }

    @Override
    public ApiResponse returnBook(Long studentId, Long bookId) {
        if (!studentRepository.existsById(studentId)) {
            throw new BookException("there is no such student: " + studentId);
        }
        if (!bookRepository.existsById(bookId)) {
            throw new BookException("there is no such book: " + bookId);
        }
        StudentBook studentBook1 = studentBookRepository.findByStudentIdAndBookId(studentId, bookId).orElseThrow(() -> new StudentException("Student Book not found") // TODO: 11/5/2023  continue logic
        );
        studentBook1.setAmount(studentBook1.getAmount() - 1);
        Optional<Student> optionalStudent = studentRepository.findById(studentId);
        Student student = optionalStudent.get();
        student.setNumberOfBooks(student.getNumberOfBooks() - 1);
        Book book = bookRepository.findById(bookId).get();
        book.setCount(book.getCount() + 1);
        StudentBook studentBook = new StudentBook();
        if (studentBook.getAmount() == 0) {
            student.setStatus(NOT_IN_DEBT);
        } else {
            student.setStatus(Status.DEBTOR);
        }
        studentRepository.save(student);
        bookRepository.save(book);
        StudentBook studentBookSaved = studentBookRepository.save(studentBook);
        StudentBookResponse studentBookResponse = modelMapper.map(studentBookSaved, StudentBookResponse.class);
        return new ApiResponse("success", true, studentBookResponse);
    }

    @Override
    public ApiResponse graduateStudentTrue(StudentRequest request, Long studentId) {
        Optional<Student> optionalStudent = studentRepository.findById(studentId);
        if (optionalStudent.isPresent()) {
            Student student = optionalStudent.get();
            if (student.getStatus().equals(NOT_IN_DEBT) || student.getNumberOfBooks() == 0) {
                student.setGraduated(true);
                Student savedStudent = studentRepository.save(student);
                StudentStatusResponse studentResponse = modelMapper.map(savedStudent, StudentStatusResponse.class);
                return new ApiResponse("successfully the student graduated!", true, studentResponse);
            }
            throw new BookException("student should return the book or books before graduate: and Number of books she/he should put back: " + optionalStudent.get().getNumberOfBooks());

        }
        throw new StudentException("there is no student with the id:  + studentId");
    }

    @Override
    public ApiResponse verifyEmail(UserRequest student) {

        return null;
    }

    @Override
    public ApiResponse registerStudent(StudentRequest request) {

        return null;
    }

    @Override
    public ApiResponse getAllMyBook(Long bookId, Long studentId) { // TODO: 11/12/2023  is that okey?
        studentBookRepository.findByStudentIdAndBookId(bookId, studentId);
        if (!studentRepository.existsById(studentId)) {
            throw new BaseException("there is no student with id " + studentId);
        }
        if (!bookRepository.existsById(bookId)) {
            throw new BookException("there is no book with id " + bookId);
        }
        List<StudentBook> optionalStudentBook = studentBookRepository.findAllBookByStudentId(studentId);
        if (!optionalStudentBook.isEmpty()) {
            List<MyBooks> books = optionalStudentBook
                    .stream()
                    .map(studentBook -> modelMapper.map(optionalStudentBook, MyBooks.class))
                    .toList();
            return new ApiResponse("success", true, books);
        }

        throw new BookException("there is no book with id " + bookId);
    }


    public Book getBookById(Long id) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isPresent()) {
            return optionalBook.get();
        }
        throw new BookException("there is no book with id: " + id);
    }

    @Override
    public ApiResponse getAllAvailableBook(int page, int size) {
        Page<Book> books = bookRepository.findAllByCountGreaterThan(0, PageRequest.of(page, size));
        return new ApiResponse("list of books", true, books.map(book -> modelMapper.map(book, BookResponse.class)));
    }

    @Override
    public ApiResponse findAll(int page, int size) {
        Page<Book> books = bookRepository.findAll(PageRequest.of(page, size));
        return new ApiResponse("list of books", true, books.map(book -> modelMapper.map(book, BookResponseById.class)));
    }

    @Override
    public ApiResponse getBooksByCategory(int page, int size) {
        return null;
    }

    @Override
    public ApiResponse getAllNotAvailableBook(int page, int size) {
        List<Book> bookPage = bookRepository.findAllByCountLessThan(1, PageRequest.of(page, size));
        if (bookPage.isEmpty()) {
            throw new BookException("there is no book");
        }
        return new ApiResponse("all deleted books", true, bookPage.stream()
                .map(book -> modelMapper.map(book, BookResponse.class))
                .collect(Collectors.toList()));
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

    public Author getAuthorById(Long authorId) {
        Optional<Author> optionalAuthor = authorRepository.findById(authorId);
        if (optionalAuthor.isPresent()) {
            return optionalAuthor.get();
        }
        throw new BookException("there is no such author");
    }

    @Override
    public ApiResponse updateBook(BookRequest request, Long id) {
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
        List<StudentResponse> responseList =
                students
                        .stream()
                        .map(student -> modelMapper.map(student, StudentResponse.class))
                        .collect(Collectors.toList());
        return new ApiResponse("success", true, responseList);
    }

    @Override
    public ApiResponse getAllGraduatedStudents(int page, int size) {
        List<Student> students = studentRepository.findAllByGraduatedTrue(Sort.by("id"), PageRequest.of(page, size));
        return new ApiResponse("success", true, students.stream()
                .map(student -> modelMapper.map(student, StudentResponse.class))
                .collect(Collectors.toList()));
    }

    @Override
    public ApiResponse deleteStudent(Long id) { // TODO: 11/22/2023 no deleting data
        Optional<Student> studentOptional = studentRepository.findStudentByIdAndGraduatedFalse(id);
        if (studentOptional.isPresent()) {
            Student student = studentOptional.get();
            if (student.getStatus().equals(NOT_IN_DEBT) && student.getNumberOfBooks()==0){
                studentRepository.deleteById(id);
//                Student savedStudent = studentRepository.save(student);
//                StudentResponse studentResponse = modelMapper.map(savedStudent, StudentResponse.class);
                return new ApiResponse("success", true, "The student has been deleted with the ID: " + id);
            }
            throw new BookException("this student has to return the books he or she took back");
        }
        throw new BookException("there is no student with this id: " + id);
    }

    @Override
    public ApiResponse updateStudent(StudentRequest request, Long studentId) {
        Optional<Student> studentOptional = studentRepository.findStudentByEmail(request.getEmail());
        if (studentOptional.isPresent()) {
            Optional<StudentBook> studentBook = studentBookRepository.findById(studentId);
            StudentBook book = studentBook.get();
            if (book.getAmount() - studentBook.get().getStudent().getNumberOfBooks() > 0) {
                Student student = studentOptional.get();
                student.setId(studentId);
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
        }

        throw new BookException("failed to update");
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
        user.setRole(Roles.builder().name(USER).build());
//        user.setDeleted(false);
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
        student.setPassword(passwordEncoder.encode(request.getPassword()));
        student.setStudentGrade(request.getStudentGrade());
        student.setSchoolName(SchoolName.SAMARQAND_SHAHRIDAGI_PREZIDENT_MAKTABI);
        student.setStatus(request.getStatus());
        student.setBooks(null);
        student.setRoleId(request.getRoleId());
        student.setGraduated(false);
        Student savedStudent = studentRepository.save(student);
        StudentResponse studentResponse = modelMapper.map(savedStudent, StudentResponse.class);
        return new ApiResponse("success", true, studentResponse);
    }

    @Override
    public ApiResponse getBookByBookName(String bookName) {
        List<Book> bookList = bookRepository.findBookByBookName(bookName);
        if (bookList != null) {
            List<BookResponse> collect = bookList.stream()
                    .map(book -> modelMapper.map(book, BookResponse.class))
                    .collect(Collectors.toList());
            return new ApiResponse("success", true, collect);
        }
        throw new BookException("the book not found");

    }
    //************TEACHER SECTION **************//
    @Override
    public ApiResponse addTeacher(UserRequest request) {
        Optional<User> optionalUser = userRepository.findByFullName(request.getFullName());
        if (optionalUser.isPresent()) {
            throw new TeacherException("teacher is already registered", HttpStatus.ALREADY_REPORTED);
        }
        User user  = new User();
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setWorkPlace(request.getWorkPlace());
        user.setPosition(request.getPosition());
        user.setSchoolName(SchoolName.SAMARQAND_SHAHRIDAGI_PREZIDENT_MAKTABI);
        user.setRoleId(request.getRoleId());
        User savedUser = userRepository.save(user);
        TeacherResponse teacherResponse = modelMapper.map(savedUser, TeacherResponse.class);
        return new ApiResponse("success", true, teacherResponse);

    }

    @Override
    public ApiResponse getTeacher(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()){
            User user = optionalUser.get();
            TeacherResponse teacherResponse = modelMapper.map(user, TeacherResponse.class);
            return new ApiResponse("success", true, teacherResponse);
        }
        throw new TeacherException("Teacher not found with id " + id, HttpStatus.NOT_FOUND);
    }

    @Override
    public ApiResponse getAllTeachers(int page, int size) {
        Page<User> users = userRepository.findAll(Sort.by("id"), PageRequest.of(page, size));
        if (!users.isEmpty()) {
            List<TeacherResponse> responseList = users
                    .stream()
                    .map(user -> modelMapper.map(user, TeacherResponse.class))
                    .toList();
            return new ApiResponse("success", true, responseList);
        }
        throw new TeacherException("there is no any teacher list ", HttpStatus.NOT_FOUND);

    }

    @Override
    public ApiResponse deleteTeacher(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent() && optionalUser.get().getStatus().equals(NOT_IN_DEBT) || optionalUser.get().getStatus().equals(NOT_TAKE_BOOK)){
            userRepository.deleteById(id);
        return new ApiResponse("success", true, optionalUser.get());
    }
    throw new TeacherException("Could not find find user to delete", HttpStatus.NOT_FOUND);
    }

    @Override
    public ApiResponse updateTeacher(Long teacherId, UserRequest request) {
        if (userRepository.existsById(teacherId)) {
            Optional<User> optionalUser = userRepository.findUserByEmail(request.getEmail());
            if (optionalUser.isPresent()) {
                User teacher = getUser(teacherId, request, optionalUser);
                User savedTeacher = userRepository.save(teacher);
                TeacherResponse teacherResponse = modelMapper.map(savedTeacher, TeacherResponse.class);
                return new ApiResponse("success",true, teacherResponse);
            }
            throw new TeacherException("there is no teacher exists by Email", HttpStatus.CONFLICT);
        }
        throw new TeacherException("there is no teacher with id: " + teacherId, HttpStatus.NOT_FOUND);
    }

    @Override
    public ApiResponse returnBookByTeacher(Long teacherId, Long bookId) {
        if (!userRepository.existsById(teacherId)) {
            throw new TeacherException("there is no teacher with id: " +teacherId, HttpStatus.NOT_FOUND);
        }
        if (!bookRepository.existsById(bookId)) {
            throw new BookException("there is no book with id: " + bookId, HttpStatus.NOT_FOUND);
        }
        User user = new User();
        if (user.getBookNumber()==0 || user.getStatus().equals(NOT_IN_DEBT) || user.getStatus().equals(NOT_TAKE_BOOK)){

        }
        Optional<Book> optionalBook = bookRepository.findById(bookId);
        Optional<User> optionalUser = userRepository.findById(teacherId);


    }

    private static User getUser(Long teacherId, UserRequest request, Optional<User> optionalUser) {
        User teacher = optionalUser.get();
        teacher.setId(teacherId);
        teacher.setFullName(request.getFullName());
        teacher.setEmail(request.getEmail());
        teacher.setWorkPlace(request.getWorkPlace());
        teacher.setPosition(request.getPosition());
        teacher.setStatus(NOT_TAKE_BOOK);
        teacher.setSchoolName(SchoolName.SAMARQAND_SHAHRIDAGI_PREZIDENT_MAKTABI);
        teacher.setRoleId(request.getRoleId());
        return teacher;
    }
    public static User getStatus(String status) {
        User user = new User();
        if ()
    }

}
