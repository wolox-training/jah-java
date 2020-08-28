package wolox.training.models.dto;

import lombok.Data;

@Data
public class BookParametersDTO {

    private String genre;
    private String author;
    private String image;
    private String title;
    private String subtitle;
    private String publisher;
    private String year;
    private int pages;
    private String isbn;

    public BookParametersDTO(){

    }

    public BookParametersDTO(String genre, String author, String image, String title, String subtitle, String publisher, String year, int pages, String isbn){
        this.genre = genre;
        this.author = author;
        this.image = image;
        this.title = title;
        this.subtitle = subtitle;
        this.publisher = publisher;
        this.year = year;
        this.pages = pages;
        this.isbn = isbn;
    }
}
