package wolox.training.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wolox.training.exceptions.BookNotFoundException;
import wolox.training.exceptions.UserIdMismatchException;
import wolox.training.exceptions.UserNotFoundException;
import wolox.training.models.Book;
import wolox.training.models.User;
import wolox.training.repositories.BookRepository;
import wolox.training.repositories.UserRepository;
import wolox.training.services.IBookService;
import wolox.training.services.IUserService;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    IBookService bookService;

    @Override
    public User create(User user) {
        return userRepository.save(user);
    }

    @Override
    public Iterable findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);
    }

    @Override
    public void delete(Long id) {
        userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        userRepository.deleteById(id);
    }

    @Override
    public User update(Long id, User user) {
        if (user.getId() != id) {
            throw new UserIdMismatchException();
        }
        userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        return userRepository.save(user);
    }

    @Override
    public User addBook(Long id, Book book) {
        User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        user.addBook(book);
        return userRepository.save(user);
    }

    @Override
    public User removeBook(Long id, Long book_id) {
        User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        Book book = bookService.findById(book_id);
        user.removeBook(book);
        return userRepository.save(user);
    }
}
