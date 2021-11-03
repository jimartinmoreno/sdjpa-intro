package guru.springframework.sdjpaintro;

import guru.springframework.sdjpaintro.domain.Book;
import guru.springframework.sdjpaintro.repositories.BookRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.List;

@SpringBootApplication
public class SdjpaIntroApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(SdjpaIntroApplication.class, args);
        BookRepository bookRepository = (BookRepository) context.getBean("bookRepository");
        List<Book> returnedBooks = bookRepository.findByNameCustomQuery("Oriely");
        System.out.println("returnedBooks" + returnedBooks);
        returnedBooks = bookRepository.findByNameNativeQuery("Domain Driven Design");
        System.out.println("returnedBooks" + returnedBooks);

    }

}
