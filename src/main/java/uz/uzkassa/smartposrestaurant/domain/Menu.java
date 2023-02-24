package uz.uzkassa.smartposrestaurant.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import uz.uzkassa.smartposrestaurant.domain.auth.User;
import uz.uzkassa.smartposrestaurant.domain.base.AbstractAuditingEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 17.10.2022 15:10
 */
@Getter
@Setter
@Table(name = "menu")
@Entity
@SQLDelete(sql = "UPDATE menu SET deleted='true' WHERE id=? ")
public class Menu extends AbstractAuditingEntity implements Serializable {

    static final long serialVersionUID = 23720L;

    @Column(name = "name")
    @NotNull
    String name;

    @Column(name = "mxik_code")
    String mxikCode;

    @Column(name = "branch_id")
    String branchId;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id", insertable = false, updatable = false)
    Branch branch;

    @Column(name = "owner_id")
    String ownerId;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", insertable = false, updatable = false)
    User owner;

    @Column(name = "photo_id")
    String photoId;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "photo_id", updatable = false, insertable = false)
    FileUpload photo;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Menu)) {
            return false;
        }
        return id != null && id.equals(((Menu) o).id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
