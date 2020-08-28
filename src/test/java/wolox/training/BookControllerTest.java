package wolox.training;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.ArgumentMatchers.eq;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import wolox.training.controllers.BookController;
import wolox.training.exceptions.BookIdMismatchException;
import wolox.training.exceptions.BookNotFoundException;
import wolox.training.models.Book;
import wolox.training.services.IBookService;

@WebMvcTest(BookController.class)
public class BookControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private IBookService bookService;

    @MockBean
    private Book book;

    private String requestJson;

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

    @Test
    public void whenDeleteWithUnknownIdThenReturnException() throws Throwable{
        Mockito.doThrow(BookNotFoundException.class).when(bookService).delete(55L);
        mvc.perform(delete("/api/books/55")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void whenUpdateWithIdMismatchThenReturnException() throws Throwable{
        Mockito.doThrow(BookIdMismatchException.class).when(bookService).update(anyLong(), any());
        mvc.perform(put("/api/books/55")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestJson))
            .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

}
