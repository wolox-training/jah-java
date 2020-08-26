package wolox.training;

import java.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import wolox.training.exceptions.BookAlreadyOwnedException;
import wolox.training.models.Book;
import wolox.training.models.User;
import wolox.training.repositories.UserRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryIntegrationTest {

    @Autowired
    private User user;
    @Autowired
    private User userTest;
    @Autowired
    private Book book;

    @Autowired
    private UserRepository userRepository;

    @Before
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
        User userSavedTest = userRepository.save(user);
        Assertions.assertTrue(userSavedTest != null);
        Assertions.assertEquals(user.getUsername(), userSavedTest.getUsername());
        Assertions.assertEquals(user.getName(), userSavedTest.getName());
        Assertions.assertEquals(user.getBirthdate(), userSavedTest.getBirthdate());
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenCreateWithoutUsernameThenThrowIllegalArgumentException(){
        userTest.setUsername(null);
        userRepository.save(userTest);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenCreateWithoutNameThenThrowIllegalArgumentException(){
        userTest.setName(null);
        userRepository.save(userTest);
    }

    @Test(expected = NullPointerException.class)
    public void whenCreateWithoutBirthdateThenThrowIllegalArgumentException(){
        userTest.setBirthdate(null);
        userRepository.save(userTest);
    }

    @Test(expected = NullPointerException.class)
    public void whenCreateWithNullBooksThenThrowNullPointerException(){
        userTest.setBooks(null);
        userRepository.save(userTest);
    }

    @Test(expected = BookAlreadyOwnedException.class)
    public void whenAddBookWithExistingBookThenThrowBookAlreadyOwnedException(){
        userTest.addBook(book);
    }

}
