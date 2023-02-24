package uz.uzkassa.smartposrestaurant.dto.activityType;

import lombok.*;
import lombok.experimental.FieldDefaults;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 10.10.2022 19:53
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CtsSyncActivityTypeDTO {

    Long id;

    Long code;

    Long parentId;

    Long parentCode;

    String nameUz;

    String nameCyrillic;

    String nameRu;
}
