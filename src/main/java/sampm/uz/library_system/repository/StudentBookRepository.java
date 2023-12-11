package sampm.uz.library_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sampm.uz.library_system.entity.StudentBook;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentBookRepository extends JpaRepository<StudentBook, Long> {

//    Optional<StudentBook> findStudentByStudentIdAndBookId(Long student_id, Long book_id);
//    List<StudentBook> findAllByStudentIdAndAmountGreaterThan(Long studentId, int amount, Pageable pageable);

    Optional<StudentBook> findByStudentIdAndBookId(Long studentId, Long bookId);
    List<StudentBook> findAllBookByStudentId(Long studentId);

}
