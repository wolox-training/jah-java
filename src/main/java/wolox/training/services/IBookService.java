package wolox.training.services;

import java.util.List;
import java.util.Optional;
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

    List<Book> findByAllFields(BookParametersDTO book);

}
