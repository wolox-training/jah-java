package wolox.training.services;

import wolox.training.models.Book;

public interface IBookService {

    Book create(Book book);

    Book findByAuthor(String author);

    void delete(Long id);

    Book update(Long id, Book book);

}
