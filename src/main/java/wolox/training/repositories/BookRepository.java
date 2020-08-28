package wolox.training.repositories;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
     * Method that find book list according to entered parameters that could be null
     * @param genre
     * @param author
     * @param image
     * @param title
     * @param subtitle
     * @param publisher
     * @param year
     * @param pages
     * @param isbn
     * @return Optional book list
     */
    @Query("SELECT b FROM Book b "
        + " WHERE (:genre is '' OR :genre is null OR b.genre = :genre)"
        + " AND (:author is '' OR :author is null OR b.author = :author)"
        + " AND (:image is '' OR :image is null OR b.image = :image)"
        + " AND (:title is '' OR :title is null OR b.title = :title)"
        + " AND (:subtitle is '' OR :subtitle is null OR b.subtitle = :subtitle)"
        + " AND (:publisher is '' OR :publisher is null OR b.publisher = :publisher)"
        + " AND (:year is '' OR :year is null OR b.year = :year)"
        + " AND (CAST(:pages as int) <= 0 OR CAST(b.pages as int) = :pages)"
        + " AND (:isbn is '' OR :isbn is null OR b.isbn = :isbn)")
    Page<Book> findByAllFields(Pageable pageable, @Param("genre") String genre,
        @Param("author") String author, @Param("image") String image,
        @Param("title") String title, @Param("subtitle") String subtitle,
        @Param("publisher") String publisher, @Param("year") String year,
        @Param("pages") int pages, @Param("isbn") String isbn);

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
