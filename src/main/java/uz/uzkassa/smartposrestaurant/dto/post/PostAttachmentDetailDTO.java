package uz.uzkassa.smartposrestaurant.dto.post;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.uzkassa.smartposrestaurant.dto.address.AddressDetailDTO;
import uz.uzkassa.smartposrestaurant.dto.file.FileDTO;
import uz.uzkassa.smartposrestaurant.enums.AttachmentType;

import java.io.Serializable;

/**
 * Powered by: Rayimjonov Shuxratjon
 * Date: 05.06.2022 23:11
 */

@Getter
@Setter
@NoArgsConstructor
public class PostAttachmentDetailDTO implements Serializable {

    AttachmentType attachmentType;

    String text;

    FileDTO photo;

    PostPhotoGalleryDetailDTO photoGallery;

    String videoUrl;

    AddressDetailDTO address;
}
