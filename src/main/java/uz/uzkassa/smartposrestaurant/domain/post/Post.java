package uz.uzkassa.smartposrestaurant.domain.post;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import uz.uzkassa.smartposrestaurant.domain.Branch;
import uz.uzkassa.smartposrestaurant.domain.base.AbstractAuditingEntity;
import uz.uzkassa.smartposrestaurant.dto.base.CommonDTO;
import uz.uzkassa.smartposrestaurant.enums.PostStatus;
import uz.uzkassa.smartposrestaurant.enums.PostType;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 17.10.2022 14:53
 */
@Getter
@Setter
@Table(name = "post")
@Entity
@SQLDelete(sql = "UPDATE post SET deleted='true' WHERE id=?")
public class Post extends AbstractAuditingEntity implements Serializable {

    @Column(name = "title")
    String title;

    @Column(name = "post_date")
    LocalDateTime postDate;

    @Column(name = "branch_id")
    String branchId;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id", insertable = false, updatable = false)
    Branch branch;

    @Enumerated(EnumType.STRING)
    @Column(name = "event_type")
    PostType postType;

    @Enumerated(EnumType.STRING)
    @Column(name = "post_status")
    PostStatus status = PostStatus.DRAFT;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Post)) {
            return false;
        }
        return id != null && id.equals(((Post) o).id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    public CommonDTO toCommonDTO() {
        return new CommonDTO(getId(), this.getTitle());
    }
}
