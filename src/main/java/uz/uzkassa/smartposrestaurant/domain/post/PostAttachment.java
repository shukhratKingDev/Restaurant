package uz.uzkassa.smartposrestaurant.domain.post;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;
import uz.uzkassa.smartposrestaurant.domain.FileUpload;
import uz.uzkassa.smartposrestaurant.domain.address.Address;
import uz.uzkassa.smartposrestaurant.domain.base.SimpleEntity;
import uz.uzkassa.smartposrestaurant.enums.AttachmentType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 17.10.2022 14:53
 */
@Getter
@Setter
@Table(name = "post_attachment")
@Entity
public class PostAttachment extends SimpleEntity implements Serializable {

    static final long serialVersionUID = 8948593832L;

    @Column(name = "photo_id")
    String photoId;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "photo_id", updatable = false, insertable = false)
    FileUpload photo;

    @Column(name = "video_url", columnDefinition = "TEXT")
    String videoUrl;

    @Column(name = "description", columnDefinition = "TEXT")
    String description;

    @Embedded
    Address address;

    @Enumerated(EnumType.STRING)
    @Column(name = "attachment_type")
    AttachmentType attachmentType;

    @Column(name = "sorder")
    Integer sorder;

    @OneToMany(mappedBy = "postAttachment", cascade = CascadeType.ALL, orphanRemoval = true)
    @Where(clause = "deleted = 'false'")
    @OrderBy("structure")
    Set<PostPhotoGallery> photoGalleries = new HashSet<>();

    @Column(name = "post_id")
    String postId;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", updatable = false, insertable = false)
    Post post;

    public void addPhotoGallery(PostPhotoGallery postPhotoGallery) {
        postPhotoGallery.setPostAttachment(this);
        postPhotoGallery.setPostAttachmentId(this.id);
        photoGalleries.add(postPhotoGallery);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PostAttachment)) {
            return false;
        }
        return id != null && id.equals(((PostAttachment) o).id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
