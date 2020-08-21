package wolox.training.services;

import wolox.training.models.Book;
import wolox.training.models.User;

public interface IUserService {

    User create(User user);

    User findByUsername(String username);

    void delete(Long id);

    User update(Long id, User user);

    User addBook(Long id, Book book);

    void removeBook(Long id, Long book_id);

}
