package sampm.uz.library_system.service;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import sampm.uz.library_system.entity.Author;
import sampm.uz.library_system.entity.Book;
import sampm.uz.library_system.entity.Student;
import sampm.uz.library_system.entity.StudentBook;
import sampm.uz.library_system.exception.BookException;
import sampm.uz.library_system.model.request.StudentRequest;
import sampm.uz.library_system.model.common.ApiResponse;
import sampm.uz.library_system.model.response.*;
import sampm.uz.library_system.repository.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class StudentServiceImpl implements StudentService {

    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final AuthorRepository authorRepository;
    private final ModelMapper modelMapper;
    private final BookRepository bookRepository;

    private final StudentBookRepository studentBookRepository;

    public StudentServiceImpl(UserRepository userRepository, StudentRepository studentRepository, AuthorRepository authorRepository, ModelMapper modelMapper, BookRepository bookRepository, StudentBookRepository studentBookRepository) {
        this.userRepository = userRepository;
        this.studentRepository = studentRepository;
        this.authorRepository = authorRepository;
        this.modelMapper = modelMapper;
        this.bookRepository = bookRepository;
        this.studentBookRepository = studentBookRepository;
    }

    @Override
    public ApiResponse addStudent(StudentRequest request) {
        Optional<Student> optionalStudent = studentRepository.findStudentByEmailAndGraduatedFalse(request.getEmail());
        if (optionalStudent.isPresent()) {
            return new ApiResponse("there is a student with that email: " + optionalStudent.get(), false, optionalStudent.get());
        }
        return null;
    }

    @Override
    public ApiResponse getStudent(Long id) {
        Optional<Student> studentOptional = studentRepository.findStudentByIdAndGraduatedFalse(id);
        if (studentOptional.isPresent()) {
            Student student = studentOptional.get();
            student.setGraduated(false);
            StudentResponse studentResponse = modelMapper.map(student, StudentResponse.class);
            return new ApiResponse("success", true, studentResponse);
        }
        throw new BookException("error: there is no student with id: " + id);
    }

    @Override
    public ApiResponse getStudents(int page, int size) {
        Page<Student> students = studentRepository.findAllByGraduatedFalse(Sort.by("id"), PageRequest.of(page, size));
        if (students != null) {
            return new ApiResponse("here list of students", students.map(student -> modelMapper.map(student, StudentResponse.class)));
        }
        throw new BookException("not found");
    }

    @Override
    public ApiResponse getStudents() {
        List<Student> students = studentRepository.findAll();
        return new ApiResponse("success", true, students.stream().map(student -> modelMapper
                        .map(student, StudentResponse.class))
                .collect(Collectors.toList()));
    }

    @Override
    public ApiResponse getBookToStudent(Long bookId, Long studentId) {
        if (!bookRepository.existsById(bookId)) {
            throw new BookException("book not found");
        }
        if (!studentRepository.existsById(studentId)) {
            throw new BookException("student not found");
        }
        StudentBook studentBook = new StudentBook();
        studentBook.setStudent_id(studentId);
        studentBook.setBook_id(bookId);

        StudentBook savedBookToStudent = studentBookRepository.save(studentBook);
        StudentBookResponse studentBookResponse = modelMapper.map(savedBookToStudent, StudentBookResponse.class);
        return new ApiResponse("success", true, studentBookResponse);
    }

    @Override
    public ApiResponse getBooksByCategory(int page, int size, String category) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Book> booksByCategory = bookRepository.findAllBooksByCategory(category, pageRequest);
        if (booksByCategory != null && booksByCategory.hasContent()){
            List<BookResponseById> collected = booksByCategory.getContent()
                    .stream()
                    .map(book -> modelMapper.map(book, BookResponseById.class))
                    .toList();
            return new ApiResponse("success", true, collected);
        }
        throw new BookException("there is no book available with categoryName " + category, HttpStatus.NOT_FOUND);
    }

    @Override
    public ApiResponse getBooksByAuthor(int page, int size, Long author_id) { // TODO: 11/22/2023 problem with getting my books from the database
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Author> authors = authorRepository.findAllById(author_id, pageRequest);
        if (authors!=null && authors.hasContent()) {
            List<BookResponseByAuthorId> collect = authors.getContent()
                    .stream()
                    .map(book -> modelMapper.map(book, BookResponseByAuthorId.class))
                    .toList();
            return new ApiResponse("success", true, collect);
        }
        throw new BookException("there is no book available with author " + author_id, HttpStatus.NOT_FOUND);
    }

    @Override
    public ApiResponse updateStudent(StudentRequest request, Long id) {
        return null;
    }

    @Override
    public ApiResponse deleteStudent(Long id) {
        return null;
    }
}
