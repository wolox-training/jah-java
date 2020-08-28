package wolox.training;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import wolox.training.exceptions.BookIdMismatchException;
import wolox.training.exceptions.BookNotFoundException;
import wolox.training.models.Book;
import wolox.training.repositories.BookRepository;
import wolox.training.services.impl.BookServiceImpl;

@SpringBootTest
public class BookServiceTest {

    @SpyBean
    private BookServiceImpl bookService;

    @MockBean
    private BookRepository bookRepository;

    @Test
    public void whenDeleteWithUnknownIdThenReturnException() throws Exception {
        Mockito.when(bookRepository.findById(55L)).thenReturn(Optional.empty());
        assertThrows(BookNotFoundException.class, () -> {
            bookService.delete(55L);
        });
    }

    @Test
    public void whenUpdateWithUnknownIdThenReturnException() throws Exception {
        Book book = new Book(55L);
        Mockito.when(bookRepository.findById(55L)).thenReturn(Optional.empty());
        assertThrows(BookNotFoundException.class, () -> {
            bookService.update(55L, book);
        });
    }

    @Test
    public void whenUpdateWithIdMismatchThenReturnException() throws Exception {
        Book book = new Book(55L);
        assertThrows(BookIdMismatchException.class, () -> {
            bookService.update(1L, book);
        });
    }

}
