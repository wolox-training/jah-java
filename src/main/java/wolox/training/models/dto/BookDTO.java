package wolox.training.models.dto;

import java.util.List;

public class BookDTO {

    private String isbn;
    private String title;
    private String subtitle;
    private List<PublisherAuthorDTO> publishers;
    private String publishDate;
    private int pagesNumber;
    private List<PublisherAuthorDTO> authors;
    private List<ImageDTO> images;

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

    public List<ImageDTO> getImages() {
        return images;
    }

    public void setImages(List<ImageDTO> images) {
        this.images = images;
    }
}
