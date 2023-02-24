package uz.uzkassa.smartposrestaurant.dto.file;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.uzkassa.smartposrestaurant.dto.base.CommonDTO;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 11.10.2022 17:22
 */
@Getter
@Setter
@NoArgsConstructor
public class FileDTO extends CommonDTO {

    String url;

    public FileDTO(String id, String name, String url) {
        super(id, name);
        this.url = url;
    }

    public FileDTO(String id, String url) {
        super(id);
        this.url = url;
    }

}
