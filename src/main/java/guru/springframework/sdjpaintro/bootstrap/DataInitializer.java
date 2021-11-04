package guru.springframework.sdjpaintro.bootstrap;

import guru.springframework.sdjpaintro.domain.AuthorUuid;
import guru.springframework.sdjpaintro.domain.Book;
import guru.springframework.sdjpaintro.domain.BookNatural;
import guru.springframework.sdjpaintro.domain.BookUuid;
import guru.springframework.sdjpaintro.domain.composite.AuthorComposite;
import guru.springframework.sdjpaintro.domain.composite.AuthorEmbedded;
import guru.springframework.sdjpaintro.domain.composite.NameId;
import guru.springframework.sdjpaintro.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile({"mysql", "default"}) // Solo se ejecuta si usamos el perfil "test" en el application.properties
public class DataInitializer implements CommandLineRunner {

    private final BookRepository bookRepository;
    private final AuthorUuidRepository authorUuidRepository;
    private final BookUuidRepository bookUuidRepository;
    private final BookNaturalRepository bookNaturalRepository;
    private final AuthorCompositeRepository authorCompositeRepository;
    private final AuthorEmbeddedRepository authorEmbeddedRepository;

    //Lo inyecta automaticamente
    public DataInitializer(BookRepository bookRepository, AuthorUuidRepository authorUuidRepository, BookUuidRepository bookUuidRepository
            , BookNaturalRepository bookNaturalRepository, AuthorCompositeRepository authorCompositeRepository, AuthorEmbeddedRepository authorEmbeddedRepository) {
        this.bookRepository = bookRepository;
        this.authorUuidRepository = authorUuidRepository;
        this.bookUuidRepository = bookUuidRepository;
        this.bookNaturalRepository = bookNaturalRepository;
        this.authorCompositeRepository = authorCompositeRepository;
        this.authorEmbeddedRepository = authorEmbeddedRepository;
    }


    @Override
    public void run(String... args) {

        bookRepository.deleteAll();
        authorUuidRepository.deleteAll();
        bookUuidRepository.deleteAll();
        bookNaturalRepository.deleteAll();
        authorCompositeRepository.deleteAll();
        authorEmbeddedRepository.deleteAll();

        Book bookDDD = new Book("Domain Driven Design", "123", "RandomHouse", null);

        //System.out.println("Id: " + bookDDD.getId());

        Book savedDDD = bookRepository.save(bookDDD);

        //System.out.println("Id: " + savedDDD.getId());

        Book bookSIA = new Book("Spring In Action", "234234", "Oriely", null);
        Book savedSIA = bookRepository.save(bookSIA);

        bookRepository.findAll().forEach(System.out::println);

        AuthorUuid authorUuid = new AuthorUuid();
        authorUuid.setFirstName("Joe");
        authorUuid.setLastName("Buck");
        AuthorUuid savedAuthor = authorUuidRepository.save(authorUuid);
        System.out.println("Saved Author UUID: " + savedAuthor.getId());

        BookUuid bookUuid = new BookUuid();
        bookUuid.setTitle("All About UUIDs");
        bookUuid.setIsbn("1234");
        bookUuid.setPublisher("Oriely");
        BookUuid savedBookUuid = bookUuidRepository.save(bookUuid);
        System.out.println("Saved Book UUID: " + savedBookUuid.getId());

        BookNatural bookNatural = new BookNatural();
        bookNatural.setTitle("Book Natural");
        bookNatural.setIsbn("1234");
        bookNatural.setPublisher("Oriely");
        BookNatural savedBookNatural = bookNaturalRepository.save(bookNatural);
        System.out.println("Saved Book Natural: " + savedBookNatural.getTitle());

        NameId nameId = new NameId("Nacho", "Martin");
        AuthorComposite authorComposite = new AuthorComposite();
        authorComposite.setFirstName(nameId.getFirstName());
        authorComposite.setLastName(nameId.getLastName());
        authorComposite.setCountry("US");
        AuthorComposite authorCompositeSaved = authorCompositeRepository.save(authorComposite);
        System.out.println("Saved Author Composite: " + authorCompositeSaved.getFirstName());

        AuthorEmbedded authorEmbedded = new AuthorEmbedded(nameId);
        authorComposite.setCountry("US");
        AuthorEmbedded authorEmbeddedSaved = authorEmbeddedRepository.save(authorEmbedded);
        System.out.println("Saved Author Embedded: " + authorEmbeddedSaved.getNameId().getFirstName());
    }
}