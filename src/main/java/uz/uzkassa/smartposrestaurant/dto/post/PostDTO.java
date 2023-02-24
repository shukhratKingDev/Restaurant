package uz.uzkassa.smartposrestaurant.dto.post;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import uz.uzkassa.smartposrestaurant.enums.PostType;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 17.10.2022 15:42
 */
@Getter
@Setter
public class PostDTO {

    String title;

    LocalDateTime postDate;

    String branchId;

    PostType postType;

    List<PostAttachmentDTO> attachments;
}
