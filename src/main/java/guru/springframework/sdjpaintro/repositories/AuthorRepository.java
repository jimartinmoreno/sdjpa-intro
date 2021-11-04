package guru.springframework.sdjpaintro.repositories;

import guru.springframework.sdjpaintro.domain.Author;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 */
//@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
}
