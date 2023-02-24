package uz.uzkassa.smartposrestaurant.dto.post;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * Powered by: Rayimjonov Shuxratjon
 * Date: 03.06.2022 23:11
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostPhotoGalleryDTO implements Serializable {

    Integer structure;

    List<String> photos;
}
