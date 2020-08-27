package wolox.training.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.sun.istack.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import org.apache.commons.lang3.StringUtils;
import org.postgresql.shaded.com.ongres.scram.common.util.Preconditions;
import wolox.training.constants.ErrorConstants;
import wolox.training.exceptions.BookAlreadyOwnedException;
import wolox.training.exceptions.BookNotFoundInCollectionException;

@Entity
@Table(name="users")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_SEQ")
    @SequenceGenerator(name = "USER_SEQ", sequenceName = "USER_SEQ")
    private long id;

    @NotNull
    @Column(nullable = false, unique = true)
    private String username;
    @NotNull
    @Column(nullable = false)
    private String name;
    @NotNull
    @Column(nullable = false)
    private LocalDate birthdate;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    private List<Book> books = new ArrayList<>();

    public User() {

    }

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        Preconditions.checkArgument(!StringUtils.isEmpty(username), ErrorConstants.USERNAME_CANNOT_NULL);
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        Preconditions.checkArgument(!StringUtils.isEmpty(name), ErrorConstants.NAME_CANNOT_NULL);
        this.name = name;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        Preconditions.checkNotNull(birthdate, ErrorConstants.BIRTHDATE_CANNOT_NULL);
        Preconditions.checkArgument(!birthdate.isAfter(LocalDate.now()), ErrorConstants.BIRTHDATE_GREATER_CURRENT_DATE);
        this.birthdate = birthdate;
    }

    public List<Book> getBooks() {
        return Collections.unmodifiableList(books);
    }

    public void setBooks(List<Book> books) {
        Preconditions.checkNotNull(books, ErrorConstants.BOOK_LEAST_ONE_BOOK);
        this.books = books;
    }

    public void addBook(Book book) {
        if (books == null) {
            books = new ArrayList<>();
        }
        if (books.contains(book)) {
            throw new BookAlreadyOwnedException();
        }
        books.add(book);
    }

    public void removeBook(Book book) {
        if(!books.contains(book)){
            throw new BookNotFoundInCollectionException();
        }
        books.remove(book);
    }

}
