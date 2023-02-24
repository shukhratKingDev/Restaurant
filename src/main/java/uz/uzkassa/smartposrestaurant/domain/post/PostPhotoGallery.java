package uz.uzkassa.smartposrestaurant.domain.post;

import lombok.Getter;
import lombok.Setter;
import uz.uzkassa.smartposrestaurant.domain.FileUpload;
import uz.uzkassa.smartposrestaurant.domain.base.BaseEntity;
import uz.uzkassa.smartposrestaurant.domain.base.SimpleEntity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 03.10.2022 12:52
 */
@Getter
@Setter
@Entity
@Table(name = "post_photo_gallery")
public class PostPhotoGallery extends BaseEntity  implements Serializable {

    static final long serialVersionUID = 15158132132L;

    @Column(name = "photo_id")
    String photoId;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "photo_id", updatable = false, insertable = false)
    FileUpload photo;

    @Column(name = "structure")
    Integer structure;

    @Column(name = "post_attachment_id")
    String postAttachmentId;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "post_attachment_id", updatable = false, insertable = false)
    PostAttachment postAttachment;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PostPhotoGallery)) {
            return false;
        }
        return id != null && id.equals(((PostPhotoGallery) o).id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
