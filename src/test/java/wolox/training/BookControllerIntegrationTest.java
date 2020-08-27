package wolox.training;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Random;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import wolox.training.controllers.BookController;
import wolox.training.exceptions.BookIdMismatchException;
import wolox.training.exceptions.BookNotFoundException;
import wolox.training.models.Book;
import wolox.training.services.IBookService;

@RunWith(MockitoJUnitRunner.class)
@WebMvcTest(BookController.class)
public class BookControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private IBookService bookService;

    @MockBean
    private Book book;

    private String requestJson;

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

        requestJson ="{\n"
            + "    \"genre\": \"Genre\",\n"
            + "    \"author\":\"Test\",\n"
            + "    \"image\":\"image.png\",\n"
            + "    \"title\":\"Book 1\",\n"
            + "    \"subtitle\":\"Sub_1\",\n"
            + "    \"publisher\":\"Publisher-1\",\n"
            + "    \"pages\":130,\n"
            + "    \"year\":\"2018\",\n"
            + "    \"isbn\":\"987654321\"\n"
            + "}";
    }

    @Test
    public void whenFindByAuthorThenReturnBook() throws Exception{
        Mockito.when(bookService.findByAuthor("Test")).thenReturn(book);
        mvc.perform(get("/api/books/author/Test")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().json(requestJson));
    }

    @Test
    public void whenPostThenReturnCreated() throws Exception{
        Mockito.when(bookService.create(book)).thenReturn(book);
        mvc.perform(post("/api/books/")
        .contentType(MediaType.APPLICATION_JSON)
        .content(requestJson))
            .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test(expected = BookNotFoundException.class)
    public void whenDeleteWithUnknownIdThenReturnException() throws Throwable{
        mvc.perform(delete("/api/books/55")
        .contentType(MediaType.APPLICATION_JSON)
        .content(requestJson));
    }

    @Test(expected = BookIdMismatchException.class)
    public void whenUpdateWithIdMismatchThenReturnException() throws Throwable{
        mvc.perform(put("/api/books/55")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestJson));
    }

}
