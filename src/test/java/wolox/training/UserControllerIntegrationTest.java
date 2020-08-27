package wolox.training;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import wolox.training.controllers.UserController;
import wolox.training.exceptions.UserIdMismatchException;
import wolox.training.exceptions.UserNotFoundException;
import wolox.training.services.IUserService;

@RunWith(MockitoJUnitRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private IUserService userService;

    String requestJson;

    @Before
    public void setUp(){
        requestJson ="{\n"
            + "    \"username\": \"training1\",\n"
            + "    \"name\":\"Training 1\",\n"
            + "    \"birthdate\":\"1990-09-15\"\n"
            + "}";
    }

    @Test(expected = UserNotFoundException.class)
    public void whenFindByUsernameWithUserNotFoundThenReturnException() throws Throwable{
        mvc.perform(get("/api/books/username/training2").contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void whenPostThenReturnCreated() throws Exception{
        mvc.perform(post("/api/users/")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestJson))
            .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test(expected = UserNotFoundException.class)
    public void whenDeleteWithUnknownIdThenReturnException() throws Throwable{
        mvc.perform(delete("/api/users/55")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestJson));
    }

    @Test(expected = UserIdMismatchException.class)
    public void whenUpdateWithIdMismatchThenReturnException() throws Throwable{
        mvc.perform(put("/api/users/55")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestJson));
    }


}
