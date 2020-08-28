package wolox.training.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ImageDTO {

    private String small;
    private String medium;
    private String large;

}
