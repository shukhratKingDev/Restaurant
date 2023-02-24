package uz.uzkassa.smartposrestaurant.dto.post;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import uz.uzkassa.smartposrestaurant.dto.base.CommonDTO;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 18.10.2022 14:18
 */
@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PostListDTO implements Serializable {

    String id;

    String title;

    CommonDTO branch;

    LocalDateTime postDate;
}
