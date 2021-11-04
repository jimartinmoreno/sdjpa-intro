package guru.springframework.sdjpaintro;

import guru.springframework.sdjpaintro.domain.Book;
import guru.springframework.sdjpaintro.repositories.BookRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.util.List;

/**
 * @DataJpaTest Annotation for a JPA test that focuses only on JPA components.
 * Using this annotation will disable full auto-configuration and instead apply only configuration
 * relevant to JPA tests. By default, tests annotated with @DataJpaTest are transactional and roll
 * back at the end of each test. They also use an embedded in-memory database
 * (replacing any explicit or usually auto-configured DataSource). The @AutoConfigureTestDatabase
 * annotation can be used to override these settings.
 * SQL queries are logged by default by setting the spring.jpa.show-sql property to true. This can be
 * disabled using the showSql attribute. If you are looking to load your full application configuration,
 * but use an embedded database, you should consider @SpringBootTest combined with
 * @AutoConfigureTestDatabase rather than this annotation
 * @AutoConfigureTestDatabase (replace = Replace.NONE): Annotation that can be applied to a test class to
 * configure a test database to use instead of the application-defined or auto-configured DataSource.
 * <p>
 * Replace.NONE: Don't replace the application default DataSource.
 */

/**
 * @TestMethodOrder is a type-level annotation that is used to configure a MethodOrderer for the test
 * methods of the annotated test class or test interface.
 *
 * @ComponentScan En caso de necesitar que se cargue alguna configuración inicial o bootstraping
 *  se puede usar este tag
 *
 * @ActiveProfiles is a class-level annotation that is used to declare which active bean definition
 * profiles should be used when loading an ApplicationContext for test classes.
 */
//@ComponentScan(basePackages = {"guru.springframework.sdjpaintro.bootstrap"})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
// Sería un test de integración por que en este caso se lanza contra MySQL en lugar de contra H2
@ActiveProfiles(value = {"mysql"})
public class SpringBootJpaTestSlice {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    private DataSource dataSource;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private EntityManager entityManager;

    /**
     * @Order is an annotation that is used to configure the order in which the annotated element
     * (i.e., field or method) should be evaluated or executed relative to other elements of the
     * same category.
     */
    @Test
    @Order(1)
    void testInfra() {
        System.out.println("dataSource: " + dataSource);
        System.out.println("jdbcTemplate: " + jdbcTemplate);
        System.out.println("entityManager: " + entityManager);
        Assertions.assertThat(dataSource).isNotNull();
        Assertions.assertThat(jdbcTemplate).isNotNull();
        Assertions.assertThat(entityManager).isNotNull();
    }

    /**
     * @Rollback is a test annotation that is used to indicate whether a test-managed transaction
     * should be rolled back after the test method has completed.
     *
     * @Commit is a test annotation that is used to indicate that a test-managed transaction should be
     * committed after the test method has completed. It is equivalent to @Rollback(value = false)
     */
    @Test
    @Order(2)
    @Rollback(value = true)
    void testJpaTestSplice() {
        long countBefore = bookRepository.count();
        Book newBook = new Book("My Book", "1235555", "Self", null);
        Book returnedBook = bookRepository.save(newBook);

        long countAfter = bookRepository.count();

        Assertions.assertThat(countBefore).isLessThan(countAfter);
        Assertions.assertThat(returnedBook).isNotNull();
        Assertions.assertThat(returnedBook.getId()).isEqualTo(newBook.getId());
    }

    @Test
    @Order(3)
    void testCustomQuery() {
        List<Book> returnedBooks = bookRepository.findByNameCustomQuery("Oriely");
        System.out.println("returnedBooks" + returnedBooks);
        Assertions.assertThat(returnedBooks).size().isGreaterThan(0);
    }

    @Test
    @Order(4)
    void testNativeQuery() {
        List<Book> returnedBooks = bookRepository.findByNameNativeQuery("Domain Driven Design");
        System.out.println("returnedBooks" + returnedBooks);
        Assertions.assertThat(returnedBooks).size().isGreaterThan(0);
    }
}
