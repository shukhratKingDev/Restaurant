package uz.uzkassa.smartposrestaurant.dto.base;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 11.21.2022 19: 46
 */
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CommonLocalizedDTO implements Serializable {

    String id;

    String nameUz;

    String nameCyrillic;

    String nameRu;

    Long code;

    @SuppressWarnings("unused")
    public CommonLocalizedDTO(String id, String nameUz, String nameCyrillic, String nameRu) {
        this.id = id;
        this.nameUz = nameUz;
        this.nameCyrillic = nameCyrillic;
        this.nameRu = nameRu;
    }
}
