package wolox.training.controllers;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import wolox.training.models.Book;
import wolox.training.services.IBookService;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private IBookService bookService;

    @GetMapping("/greeting")
    public String greeting(@RequestParam(name = "name", required = false, defaultValue = "World") String name, Model model){
        model.addAttribute("name", name);
        return "greeting";
    }

    /**
     * Services that find a book according to the author entered by parameter
     * @param author
     * @return Book object
     */
    @GetMapping("/author/{author}")
    public Book findByAuthor(@PathVariable String author){
        return bookService.findByAuthor(author);
    }

    /**
     * Service that create a new Book with the request body data
     * @param book
     * @return Book object created
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Book create(@RequestBody Book book){
        return bookService.create(book);
    }

    /**
     * Service that validate if Book exists and then delete it
     * @param id
     */
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        bookService.delete(id);
    }

    /**
     * Service that validate if Book exist and then update the data with the request body data
     * @param book
     * @param id
     * @return Book object updated
     */
    @PutMapping("/{id}")
    public Book updateBook(@PathVariable Long id, @RequestBody Book book) {
        return bookService.update(id, book);
    }

    /**
     * Services that find a book according to the author entered by parameter
     * @param isbn
     * @return Book object
     */
    @GetMapping("/author/{author}")
    public ResponseEntity<Book> findByIsbn(@PathVariable String isbn){
        Optional<Book> book = bookService.findByIsbn(isbn);
        if(book.isPresent()){
            return new ResponseEntity<>(book.get(), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(bookService.createByOpenLibrary(isbn), HttpStatus.CREATED);
        }
    }

}
