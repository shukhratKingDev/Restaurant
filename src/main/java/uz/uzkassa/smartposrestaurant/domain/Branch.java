package uz.uzkassa.smartposrestaurant.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import uz.uzkassa.smartposrestaurant.domain.address.Address;
import uz.uzkassa.smartposrestaurant.domain.auth.User;
import uz.uzkassa.smartposrestaurant.domain.base.AbstractAuditingEntity;
import uz.uzkassa.smartposrestaurant.dto.base.CommonDTO;
import uz.uzkassa.smartposrestaurant.enums.CommonStatus;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Powered by Shuxratjon Rayimjonov
 * Date: 06.10.2022 18:01
 */

@Getter
@Setter
@Entity
@Table(name = "branch")
@SQLDelete(sql = "UPDATE branch set deleted='true' WHERE  id=?")
public class Branch extends AbstractAuditingEntity implements Serializable {

    static final long serialVersionUID = 8L;

    @Column(name = "name")
    String name;

    @Embedded
    Address address;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    CommonStatus status = CommonStatus.ACTIVE;

    @Column(name = "owner_id")
    String ownerId;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", insertable = false, updatable = false)
    User owner;

    @Column(name = "phone_number")
    String phoneNumber;

    @Column(name = "photo_id")
    String photoId;

    @Column(name = "main", columnDefinition = "boolean default false")
    boolean main = false;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "photo_id", insertable = false, updatable = false)
    FileUpload photo;

    public CommonDTO toCommonDTO() {
        return new CommonDTO(getId(), getName());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Branch)) {
            return false;
        }
        return id != null && id.equals(((Branch) o).id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
