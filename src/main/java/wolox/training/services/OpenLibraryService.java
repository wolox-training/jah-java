package wolox.training.services;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Optional;
import jdk.internal.joptsimple.internal.Strings;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import wolox.training.constants.Constants;
import wolox.training.models.dto.BookDTO;

@Service
public class OpenLibraryService {

    public Optional<BookDTO> bookInfo(String isbn) throws Exception{
        String param = "ISBN:" + isbn;
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(String.format(Constants.URL_OPEN_LIBRARY_FORMAT, param), String.class);

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
