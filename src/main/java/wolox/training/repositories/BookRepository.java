package wolox.training.repositories;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

    /**
     * Method that find a book according to the isbn entered parameter
     * @param isbn
     * @return Optional Book object
     */
    Optional<Book> findByIsbn(String isbn);

    /**
     * Method that find a book list according to the publisher, genre and year entered parameter
     * @param publisher
     * @param genre
     * @param year
     * @return
     */
    @Query("SELECT b FROM Book b"
        + " WHERE (:publisher is null or b.publisher = :publisher)"
        + " AND (:genre is null or b.genre = :genre)"
        + " AND (:year is null or b.genre = :year)")
    Optional<List<Book>> findByPublisherAndGenreAndYear(@Param("publisher") String publisher, @Param("genre") String genre, @Param("year") String year);

}
