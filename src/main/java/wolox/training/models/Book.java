package wolox.training.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.sun.istack.NotNull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import org.postgresql.shaded.com.ongres.scram.common.util.Preconditions;
import org.apache.commons.lang3.StringUtils;
import wolox.training.constants.ErrorConstants;

@Entity
@Table(name = "book")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BOOK_SEQ")
    @SequenceGenerator(name = "BOOK_SEQ", sequenceName = "BOOK_SEQ")
    private long id;

    @Column(nullable = true)
    private String genre;
    @NotNull
    @Column(nullable = false)
    private String author;
    @NotNull
    @Column(nullable = false)
    private String image;
    @NotNull
    @Column(nullable = false)
    private String title;
    @NotNull
    @Column(nullable = false)
    private String subtitle;
    @NotNull
    @Column(nullable = false)
    private String publisher;
    @NotNull
    @Column(nullable = false)
    private String year;
    @NotNull
    @Column(nullable = false)
    private int pages;
    @NotNull
    @Column(nullable = false, unique = true)
    private String isbn;

    @ManyToMany(mappedBy = "books")
    private List<User> users = new ArrayList<>();

    public Book(){
    }

    public Book(long id){
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        Preconditions.checkArgument(!StringUtils.isEmpty(genre), ErrorConstants.GENRE_VALID_VALUE);
        this.genre = genre;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        Preconditions.checkArgument(!StringUtils.isEmpty(author), ErrorConstants.AUTHOR_CANNOT_NULL);
        this.author = author;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        Preconditions.checkArgument(!StringUtils.isEmpty(author), ErrorConstants.IMAGE_CANNOT_NULL);
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        Preconditions.checkArgument(!StringUtils.isEmpty(title), ErrorConstants.TITLE_CANNOT_NULL);
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        Preconditions.checkArgument(!StringUtils.isEmpty(subtitle), ErrorConstants.SUBTITLE_CANNOT_NULL);
        this.subtitle = subtitle;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        Preconditions.checkArgument(!StringUtils.isEmpty(publisher), ErrorConstants.PUBLISHER_CANNOT_NULL);
        this.publisher = publisher;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        Preconditions.checkNotNull(year, ErrorConstants.YEAR_CANNOT_NULL);
        Preconditions.checkArgument(StringUtils.isNumeric(year), ErrorConstants.YEAR_MUST_NUMBER);
        this.year = year;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        Preconditions.checkNotNull(pages, ErrorConstants.PAGE_CANNOT_NULL);
        Preconditions.checkArgument(pages > 0, ErrorConstants.PAGE_GREATER_ZERO);
        this.pages = pages;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        Preconditions.checkNotNull(isbn, ErrorConstants.ISBN_CANNOT_NULL);
        Preconditions.checkArgument(StringUtils.isNumeric(isbn), ErrorConstants.ISBN_MUST_NUMBER);
        this.isbn = isbn;
    }

    public List<User> getUsers() {
        return Collections.unmodifiableList(users);
    }
}
