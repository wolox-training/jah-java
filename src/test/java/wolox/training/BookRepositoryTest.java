package wolox.training;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import wolox.training.models.Book;
import wolox.training.repositories.BookRepository;

@AutoConfigureTestDatabase(replace = Replace.NONE)
@DataJpaTest
public class BookRepositoryTest {

    private Book book;
    private Book bookSaved;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private TestEntityManager entityManager;

    @BeforeEach
    public void setUp(){
        book = new Book();
        book.setGenre("Genre");
        book.setAuthor("Test");
        book.setImage("image.png");
        book.setTitle("Book 1");
        book.setSubtitle("Sub_1");
        book.setPublisher("Publisher-1");
        book.setPages(130);
        book.setYear("2018");
        book.setIsbn("987654321");
        bookSaved = new Book();
        bookSaved.setGenre("Genre 2");
        bookSaved.setAuthor("Test 2");
        bookSaved.setImage("image.png");
        bookSaved.setTitle("Book 2");
        bookSaved.setSubtitle("Sub_2");
        bookSaved.setPublisher("Publisher-2");
        bookSaved.setPages(120);
        bookSaved.setYear("2012");
        bookSaved.setIsbn("9876322321");
        entityManager.persistAndFlush(bookSaved);
    }

    @Test
    public void whenFindByAuthorThenReturnBook(){
        Book bookFound = bookRepository.findFirstByAuthorIgnoreCase(bookSaved.getAuthor()).orElse(null);
        Assertions.assertNotNull(bookFound);
        Assertions.assertEquals(bookSaved.getAuthor(), bookFound.getAuthor());
    }

    @Test
    public void whenCreateWithoutGenreThenThrowIllegalArgumentException(){
        assertThrows(IllegalArgumentException.class, () -> {
            book.setGenre(null);
            bookRepository.save(book);
        });
    }

    @Test
    public void whenCreateWithoutAuthorThenThrowIllegalArgumentException(){
        assertThrows(IllegalArgumentException.class, () -> {
            book.setAuthor(null);
            bookRepository.save(book);
        });
    }

    @Test
    public void whenCreateWithoutYearThenThrowIllegalArgumentException(){
        assertThrows(IllegalArgumentException.class, () -> {
            book.setYear(null);
            bookRepository.save(book);
        });
    }

    @Test
    public void whenCreateWithoutIsbn_thenThrowIllegalArgumentException(){
        assertThrows(IllegalArgumentException.class, () -> {
            book.setIsbn(null);
            bookRepository.save(book);
        });
    }

    @Test
    public void whenCreateWithNegativePages_thenThrowIllegalArgumentException(){
        assertThrows(IllegalArgumentException.class, () -> {
            book.setPages(-1);
            bookRepository.save(book);
        });
    }

    @Test
    public void whenFindByAllFieldsExcludeSomeoneThenReturnBookList(){
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<Book> booksFound = bookRepository.findByAllFields(pageRequest, bookSaved.getGenre(),bookSaved.getAuthor(),null,
            null,null,bookSaved.getPublisher(),null,
            -1,null);

        Assertions.assertNotNull(booksFound);
        Assertions.assertTrue(booksFound.getContent().size() > 0);
    }

}
