package uz.uzkassa.smartposrestaurant.domain.permission;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import uz.uzkassa.smartposrestaurant.domain.auth.User;
import uz.uzkassa.smartposrestaurant.domain.base.BaseEntity;

import javax.persistence.*;
import java.io.Serializable;

import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.FetchType.LAZY;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 14.10.2022 11:06
 */
@Entity
@Table(name = "user_permission", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "permission_code"})})
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserPermission extends BaseEntity implements Serializable {

    static final long serialVersionUID = 68958341L;

    @Column(name = "user_id", nullable = false)
    String userId;

    @ManyToOne(cascade = PERSIST, fetch = LAZY)
    @JoinColumn(name = "user_id", updatable = false, insertable = false)
    User user;

    @Column(name = "permission_code")
    String permissionCode;

    @ManyToOne(cascade = PERSIST, fetch = LAZY)
    @JoinColumn(
        name = "permission_code",
        referencedColumnName = "code",
        updatable = false,
        insertable = false,
        foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT)
    )
    Permission permission;

    @Column(name = "has_access", columnDefinition = "boolean default true")
    boolean hasAccess;

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserPermission)) {
            return false;
        }
        return id != null && id.equals(((UserPermission) o).id);
    }
}
