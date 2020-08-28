package wolox.training;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import wolox.training.exceptions.BookAlreadyOwnedException;
import wolox.training.models.Book;
import wolox.training.models.User;
import wolox.training.repositories.UserRepository;

@AutoConfigureTestDatabase(replace = Replace.NONE)
@DataJpaTest
public class UserRepositoryTest {

    private User user;
    private User userTest;
    private Book book;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestEntityManager entityManager;

    @BeforeEach
    public void setUp(){
        LocalDate date = LocalDate.parse("1990-01-08");
        user = new User();
        user.setUsername("training2");
        user.setName("Training 2");
        user.setBirthdate(date);

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
        userTest = new User();
        userTest.setUsername("training3");
        userTest.setName("Training 3");
        userTest.setBirthdate(date);
        userTest.addBook(book);
    }

    @Test
    public void whenCreateThenReturnUser(){
        User userSavedTest = entityManager.persistAndFlush(user);
        Assertions.assertTrue(userSavedTest != null);
        Assertions.assertEquals(user.getUsername(), userSavedTest.getUsername());
        Assertions.assertEquals(user.getName(), userSavedTest.getName());
        Assertions.assertEquals(user.getBirthdate(), userSavedTest.getBirthdate());
    }

    @Test
    public void whenCreateWithoutUsernameThenThrowIllegalArgumentException(){
        assertThrows(IllegalArgumentException.class, () ->{
            userTest.setUsername(null);
            userRepository.save(userTest);
        });

    }

    @Test
    public void whenCreateWithoutNameThenThrowIllegalArgumentException(){
        assertThrows(IllegalArgumentException.class, () ->{
            userTest.setName(null);
            userRepository.save(userTest);
        });
    }

    @Test
    public void whenCreateWithoutBirthdateThenThrowIllegalArgumentException(){
        assertThrows(IllegalArgumentException.class, () ->{
            userTest.setBirthdate(null);
            userRepository.save(userTest);
        });
    }

    @Test
    public void whenCreateWithNullBooksThenThrowNullPointerException(){
        assertThrows(IllegalArgumentException.class, () ->{
            userTest.setBooks(null);
            userRepository.save(userTest);
        });
    }

    @Test
    public void whenAddBookWithExistingBookThenThrowBookAlreadyOwnedException(){
        assertThrows(BookAlreadyOwnedException.class, () -> {
            userTest.addBook(book);
        });
    }

}
