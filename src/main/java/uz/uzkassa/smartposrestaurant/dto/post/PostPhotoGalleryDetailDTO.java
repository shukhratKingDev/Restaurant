package uz.uzkassa.smartposrestaurant.dto.post;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.uzkassa.smartposrestaurant.dto.file.FileDTO;

import java.io.Serializable;
import java.util.List;

/**
 * Powered by: Rayimjonov Shuxratjon
 * Date: 05.06.2022 23:11
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostPhotoGalleryDetailDTO implements Serializable {

    Integer structure;

    List<FileDTO> photos;
}
