package wolox.training.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class BookDTO {

    private String isbn;
    private String title;
    private String subtitle;
    private List<PublisherAuthorDTO> publishers;
    @JsonProperty("publish_date")
    private String publishDate;
    @JsonProperty("number_of_pages")
    private int pagesNumber;
    private List<PublisherAuthorDTO> authors;
    @JsonProperty("cover")
    private ImageDTO image;

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public List<PublisherAuthorDTO> getPublishers() {
        return publishers;
    }

    public void setPublishers(List<PublisherAuthorDTO> publishers) {
        this.publishers = publishers;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public int getPagesNumber() {
        return pagesNumber;
    }

    public void setPagesNumber(int pagesNumber) {
        this.pagesNumber = pagesNumber;
    }

    public List<PublisherAuthorDTO> getAuthors() {
        return authors;
    }

    public void setAuthors(List<PublisherAuthorDTO> authors) {
        this.authors = authors;
    }

    public ImageDTO getImage() {
        return image;
    }

    public void setImages(ImageDTO image) {
        this.image = image;
    }
}
