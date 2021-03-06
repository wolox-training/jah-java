package wolox.training.services;

import java.util.Optional;
import wolox.training.models.Book;

public interface IBookService {

    Book create(Book book);

    Book findByAuthor(String author);

    void delete(Long id);

    Book update(Long id, Book book);

    Book findById(Long id);

    Optional<Book> findByIsbn(String isbn);

    Book createByOpenLibrary(String isbn);

}
