package wolox.training.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import wolox.training.models.Book;
import wolox.training.models.User;
import wolox.training.services.IUserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private IUserService userService;

    /**
     * Service that return all users
     * @return Iterable user objects
     */
    @GetMapping
    public Iterable findAll(){
        return userService.findAll();
    }

    /**
     * Service that Find user though username entered parameter
     * @param username
     * @return User object found
     */
    @GetMapping("/username/{username}")
    public User findByUsername(@PathVariable String username) {
        return userService.findByUsername(username);
    }

    /**
     * Service that Create a new user
     * @param user
     * @return User object created
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User create(@RequestBody User user) {
        return userService.create(user);
    }

    /**
     * Service that Delete user according to id entered parameter
     * @param id
     */
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }

    /**
     * Service that validate if User exists and then update user data
     * @param id
     * @param user
     * @return User object updated
     */
    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User user) {
        return userService.update(id, user);
    }

    /**
     * Service that add a new book to books collection of user
     * @param id
     * @param book
     * @return User object with books collection updated
     */
    @PatchMapping("/{id}/book")
    public User addBook(@PathVariable Long id, @RequestBody Book book) {
        return userService.addBook(id, book);
    }

    /**
     * Service that validate if book exists in table and books collection of user and then remove it of books collection
     * @param id
     * @param book_id
     * @return User object with books collection updated
     */
    @DeleteMapping("/{id}/book/{book_id}")
    public User removeBook(@PathVariable Long id, @PathVariable Long book_id) {
        return userService.removeBook(id, book_id);
    }

}
