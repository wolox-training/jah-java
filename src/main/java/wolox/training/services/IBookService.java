package wolox.training.services;

import java.util.Optional;
import org.springframework.data.domain.Page;
import wolox.training.models.Book;
import wolox.training.models.dto.BookParametersDTO;

public interface IBookService {

    Book create(Book book);

    Book findByAuthor(String author);

    void delete(Long id);

    Book update(Long id, Book book);

    Book findById(Long id);

    Optional<Book> findByIsbn(String isbn);

    Book createByOpenLibrary(String isbn);

    Page<Book> findByAllFields(BookParametersDTO book, int page, int size, String sortBy, String order);

}
