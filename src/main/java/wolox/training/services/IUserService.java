package wolox.training.services;

import wolox.training.models.Book;
import wolox.training.models.User;

public interface IUserService {

    User create(User user);

    Iterable findAll();

    User findByUsername(String username);

    void delete(Long id);

    User update(Long id, User user);

    User addBook(Long id, Book book);

    User removeBook(Long id, Long book_id);

}
