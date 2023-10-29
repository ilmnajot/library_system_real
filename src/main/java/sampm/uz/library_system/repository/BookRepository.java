package sampm.uz.library_system.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sampm.uz.library_system.entity.Book;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findBookByIsbn(Long isbn);

//    Page<List<Book>> findAllByCount(Sort sort, Pageable pageable);



//    @Query("select b from Book b where b.bookName like %:partialBookName%")
    @Query(value = "select  * from books where books.bookName like %?1%", nativeQuery = true)
    List<Book> findBookByBookName(String bookName);

//    Page<List<Book>> findAll(Sort sort, PageRequest request);
    List<Book> findAllByCountLessThan(int count);
    List<Book> findAllByCountGreaterThan(int count);

    boolean existsByIdAndCountGreaterThan(Long id, int count);
    boolean existsByIdAndCountLessThan(Long id, int count);

//    Page<Book> findAllByCountEmpty(Sort sort, Pageable pageable);

}
