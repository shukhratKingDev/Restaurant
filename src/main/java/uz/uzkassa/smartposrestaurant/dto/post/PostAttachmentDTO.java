package uz.uzkassa.smartposrestaurant.dto.post;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.uzkassa.smartposrestaurant.dto.address.AddressDTO;
import uz.uzkassa.smartposrestaurant.enums.AttachmentType;

import java.io.Serializable;

/**
 * Powered by: Rayimjonov Shuxratjon
 * Date: 03.06.2022 23:11
 */

@Getter
@Setter
@NoArgsConstructor
public class PostAttachmentDTO implements Serializable {

    AttachmentType attachmentType;

    String text;

    String photoId;

    PostPhotoGalleryDTO photoGallery;

    String videoUrl;

    AddressDTO address;
}
