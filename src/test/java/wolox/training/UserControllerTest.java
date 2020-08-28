package wolox.training;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.util.NestedServletException;
import wolox.training.controllers.UserController;
import wolox.training.exceptions.BookIdMismatchException;
import wolox.training.exceptions.BookNotFoundException;
import wolox.training.exceptions.UserIdMismatchException;
import wolox.training.exceptions.UserNotFoundException;
import wolox.training.models.User;
import wolox.training.services.IUserService;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private IUserService userService;

    @MockBean
    private User user;

    String requestJson;

    @BeforeEach
    public void setUp(){
        LocalDate date = LocalDate.parse("1990-09-15");
        user = new User();
        user.setUsername("training1");
        user.setName("Training 1");
        user.setBirthdate(date);
        requestJson ="{\n"
            + "    \"username\": \"training1\",\n"
            + "    \"name\":\"Training 1\",\n"
            + "    \"birthdate\":\"1990-09-15\"\n"
            + "}";
    }

    @Test
    public void whenFindByAuthorThenReturnBook() throws Throwable{
        Mockito.when(userService.findByUsername(eq("training1"))).thenReturn(user);
            mvc.perform(get("/api/users/username/training1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(requestJson));

    }

    @Test
    public void whenPostThenReturnCreated() throws Exception{
        Mockito.when(userService.create(user)).thenReturn(user);
        mvc.perform(post("/api/users/")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestJson))
            .andExpect(status().isCreated());
    }

    @Test
    public void whenDeleteWithUnknownIdThenReturnException() throws Throwable{
        Mockito.doThrow(BookNotFoundException.class).when(userService).delete(5L);
        mvc.perform(delete("/api/users/5")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void whenUpdateWithIdMismatchThenReturnException() throws Throwable{
        Mockito.doThrow(BookIdMismatchException.class).when(userService).update(anyLong(), any());
        mvc.perform(put("/api/users/55")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestJson))
            .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }


}
