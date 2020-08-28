package wolox.training.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
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

}
