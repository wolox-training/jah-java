package wolox.training.controllers;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
import wolox.training.models.dto.ResponsePagingData;
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
    @GetMapping("/isbn/{isbn}")
    public ResponseEntity<Book> findByIsbn(@PathVariable String isbn){
        Optional<Book> book = bookService.findByIsbn(isbn);
        if(book.isPresent()){
            return new ResponseEntity<>(book.get(), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(bookService.createByOpenLibrary(isbn), HttpStatus.CREATED);
        }
    }

    /**
     * Service that find book list according to entered parameters
     * @param genre
     * @param author
     * @param image
     * @param title
     * @param subtitle
     * @param publisher
     * @param year
     * @param pages
     * @param isbn
     * @return Book list or Book not found
     */
    @GetMapping
    public ResponsePagingData<List<Book>> findByAllFields(@RequestParam(required = false, defaultValue = "") String genre,
        @RequestParam(required = false, defaultValue = "") String author,
        @RequestParam(required = false, defaultValue = "") String image,
        @RequestParam(required = false, defaultValue = "") String title,
        @RequestParam(required = false, defaultValue = "") String subtitle,
        @RequestParam(required = false, defaultValue = "") String publisher,
        @RequestParam(required = false, defaultValue = "") String year,
        @RequestParam(required = false, defaultValue = "0") int pages,
        @RequestParam(required = false, defaultValue = "") String isbn,
        @RequestParam(required = false, defaultValue = "0") int page,
        @RequestParam(required = false, defaultValue = "10") int limit,
        @RequestParam(required = false, defaultValue = "id") String sortBy,
        @RequestParam(required = false, defaultValue = "asc") String order){
        Page<Book> resultPage = bookService.findByAllFields(genre, author, image, title, subtitle, publisher, year, pages, isbn, page, limit, sortBy, order);
        return new ResponsePagingData<>(limit, limit, 0, resultPage.getTotalPages(), resultPage.getTotalElements(),
            resultPage.getNumber() - 1, resultPage.getNumber(), resultPage.getNumber() == resultPage.getTotalPages() ? resultPage
            .getNumber() : resultPage.getNumber() + 1, resultPage.getContent());
    }

}
