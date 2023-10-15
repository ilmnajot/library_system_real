package sampm.uz.library_system.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sampm.uz.library_system.entity.Book;

import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findBookByBookNameAndAvailableTrue(String name);
    Optional<Book> findBookByIdAndAvailableTrue(Long id);
    Page<Book> findAllByAvailableTrue(Pageable pageable);
    Page<Book> findAllByAvailableFalse(Pageable pageable);
}
