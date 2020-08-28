package wolox.training;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import wolox.training.exceptions.BookIdMismatchException;
import wolox.training.exceptions.BookNotFoundException;
import wolox.training.exceptions.UserIdMismatchException;
import wolox.training.exceptions.UserNotFoundException;
import wolox.training.models.Book;
import wolox.training.models.User;
import wolox.training.repositories.UserRepository;
import wolox.training.services.impl.UserServiceImpl;

@SpringBootTest
public class UserServiceTest {

    @SpyBean
    private UserServiceImpl userService;

    @MockBean
    private UserRepository userRepository;

    @Test
    public void whenDeleteWithUnknownIdThenReturnException() throws Exception {
        Mockito.when(userRepository.findById(55L)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> {
            userService.delete(55L);
        });
    }

    @Test
    public void whenUpdateWithUnknownIdThenReturnException() throws Exception {
        User user = new User(2L);
        Mockito.when(userRepository.findById(2L)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> {
            userService.update(2L, user);
        });
    }

    @Test
    public void whenUpdateWithIdMismatchThenReturnException() throws Exception {
        User user = new User(2L);
        assertThrows(UserIdMismatchException.class, () -> {
            userService.update(1L, user);
        });
    }

}
