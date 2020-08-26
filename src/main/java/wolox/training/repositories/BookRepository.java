package wolox.training.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wolox.training.models.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    /**
     * This method find a book according to the author entered through a parameter
     * @param author
     * @return Optional Book object
     */
    Optional<Book> findFirstByAuthorIgnoreCase(String author);

}
