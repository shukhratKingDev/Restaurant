package uz.uzkassa.smartposrestaurant.dto.post;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import uz.uzkassa.smartposrestaurant.dto.base.CommonDTO;
import uz.uzkassa.smartposrestaurant.dto.file.FileDTO;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 17.10.2022 17:19
 */
@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PostDetailDTO  implements Serializable {

    String id;

    String title;

    CommonDTO branch;

    List<PostAttachmentDetailDTO> attachments = new ArrayList<>();

    @JsonIgnore
    public void addAttachment(PostAttachmentDetailDTO postAttachmentDetailDTO) {
        attachments.add(postAttachmentDetailDTO);
    }
}
