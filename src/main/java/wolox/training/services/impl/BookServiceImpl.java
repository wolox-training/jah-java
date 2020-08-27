package wolox.training.services.impl;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import wolox.training.exceptions.BookIdMismatchException;
import wolox.training.exceptions.BookNotFoundException;
import wolox.training.models.Book;
import wolox.training.models.dto.BookDTO;
import wolox.training.repositories.BookRepository;
import wolox.training.services.IBookService;
import wolox.training.services.OpenLibraryService;

@Service
public class BookServiceImpl implements IBookService {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    OpenLibraryService openLibraryService;

    public Book create(Book book){
        return bookRepository.save(book);
    }

    public Book findByAuthor(String author){
        return bookRepository.findFirstByAuthorIgnoreCase(author).orElseThrow(BookNotFoundException::new);
    }

    public void delete(Long id){
        bookRepository.findById(id)
            .orElseThrow(BookNotFoundException::new);
        bookRepository.deleteById(id);
    }

    public Book update(Long id, Book book){
        if (book.getId() != id) {
            throw new BookIdMismatchException();
        }
        bookRepository.findById(id)
            .orElseThrow(BookNotFoundException::new);
        return bookRepository.save(book);
    }

    @Override
    public Book findById(Long id) {
        return bookRepository.findById(id)
            .orElseThrow(BookNotFoundException::new);
    }

    @Override
    public Optional<Book> findByIsbn(String isbn) {
        return bookRepository.findByIsbn(isbn);
    }

    @Override
    public Book createByOpenLibrary(String isbn) {
        try {
            BookDTO bookDTO = openLibraryService.bookInfo(isbn)
                .orElseThrow(BookNotFoundException::new);
            Book bookLibrary = convertBookDTO(bookDTO);
            return bookRepository.save(bookLibrary);
        } catch (Exception e) {
            throw new BookNotFoundException();
        }
    }

    @Override
    public Page<Book> findByAllFields(String genre, String author, String image, String title,
        String subtitle, String publisher, String year, int pages, String isbn, int page, int size, String sortBy, String order) {
        PageRequest pageRequest = PageRequest.of(page, size, order.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending());
        return bookRepository.findByAllFields(pageRequest, genre, author, image, title, subtitle, publisher, year, pages, isbn);
    }

    private Book convertBookDTO(BookDTO bookDTO){
        Book book = new Book();
        book.setGenre("-");
        book.setAuthor(bookDTO.getAuthors().get(0).getName());
        book.setImage(bookDTO.getImage().getMedium());
        book.setTitle(bookDTO.getTitle());
        book.setSubtitle(bookDTO.getSubtitle());
        book.setPublisher(bookDTO.getPublishers().get(0).getName());
        book.setYear(bookDTO.getPublishDate());
        book.setPages(bookDTO.getPagesNumber());
        book.setIsbn(bookDTO.getIsbn());
        return book;
    }

}
