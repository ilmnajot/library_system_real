package sampm.uz.library_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sampm.uz.library_system.entity.StudentBook;

import java.util.Optional;

@Repository
public interface StudentBookRepository extends JpaRepository<StudentBook, Long> {

    Optional<StudentBook> findByStudent_idAndBook_id(Long studentId, Long bookId);
}
