package uz.uzkassa.smartposrestaurant.dto.soliq;

import lombok.*;

import java.io.Serializable;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 18.10.2022 16:43
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PackageDTO implements Serializable {

    private String code;

    private String nameRu;

    private String nameUz;

    public String getName() {
        return getNameRu();
    }
}
