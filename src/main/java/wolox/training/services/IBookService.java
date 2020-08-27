package wolox.training.services;

import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.query.Param;
import wolox.training.models.Book;

public interface IBookService {

    Book create(Book book);

    Book findByAuthor(String author);

    void delete(Long id);

    Book update(Long id, Book book);

    Book findById(Long id);

    Optional<Book> findByIsbn(String isbn);

    Book createByOpenLibrary(String isbn);

    List<Book> findByAllFields(String genre, String author, String image, String title, String subtitle,
        String publisher, String year, int pages, String isbn);

}
