package wolox.training;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;
import wolox.training.models.Book;
import wolox.training.repositories.BookRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class BookRepositoryIntegrationTest {


    @Autowired
    private Book book;
    @Autowired
    private Book bookSaved;

    @Autowired
    private BookRepository bookRepository;

    @Before
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
        bookSaved = bookRepository.save(bookSaved);
    }

    @Test
    public void whenFindByAuthorThenReturnBook(){
        Book bookFound = bookRepository.findFirstByAuthorIgnoreCase(bookSaved.getAuthor()).orElse(null);
        Assertions.assertNotNull(bookFound);
        Assertions.assertEquals(bookSaved.getAuthor(), bookFound.getAuthor());
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenCreateWithoutGenreThenThrowIllegalArgumentException(){
        book.setGenre(null);
        bookRepository.save(book);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenCreateWithoutAuthorThenThrowIllegalArgumentException(){
        book.setAuthor(null);
        bookRepository.save(book);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenCreateWithoutYearThenThrowIllegalArgumentException(){
        book.setYear(null);
        bookRepository.save(book);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenCreateWithoutIsbn_thenThrowIllegalArgumentException(){
        book.setIsbn(null);
        bookRepository.save(book);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenCreateWithNegativePages_thenThrowIllegalArgumentException(){
        book.setPages(-1);
        bookRepository.save(book);
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
