package guru.springframework.sdjpaintro.repositories;

import guru.springframework.sdjpaintro.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 *
 */
public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("select b from Book b where b.publisher = :publisher")
    List<Book> findByNameCustomQuery(@Param("publisher") String publisher);

    @Query(value = "select * from book as b where b.title = :title", nativeQuery = true)
    List<Book> findByNameNativeQuery(@Param("title") String title);


}
