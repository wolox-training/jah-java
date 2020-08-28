package wolox.training.services;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Optional;
import jdk.internal.joptsimple.internal.Strings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import wolox.training.constants.Constants;
import wolox.training.models.dto.BookDTO;

public class OpenLibraryService {

    @Value("{$use.wiremock}")
    private static int useWiremock;

    public Optional<BookDTO> bookInfo(String isbn) throws Exception{
        String param = "ISBN:" + isbn;
        RestTemplate restTemplate = new RestTemplate();
        String url = String.format(Constants.URL_OPEN_LIBRARY_FORMAT, isbn);
        if(useWiremock == 1){
            url = Constants.URL_OPEN_LIBRARY_WIREMOCK;
        }
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        JsonNode root = objectMapper.readTree(response.getBody());
        JsonNode isbnNode = root.path(param);
        BookDTO bookDTO = !Strings.isNullOrEmpty(isbnNode.toString()) ? objectMapper.readValue(isbnNode.toString(), BookDTO.class):null;
        if(bookDTO != null){
            bookDTO.setIsbn(isbn);
        }
        return Optional.ofNullable(bookDTO);
    }

}