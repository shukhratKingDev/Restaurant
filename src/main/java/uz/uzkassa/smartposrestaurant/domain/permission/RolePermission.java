package uz.uzkassa.smartposrestaurant.domain.permission;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import uz.uzkassa.smartposrestaurant.domain.base.BaseEntity;
import uz.uzkassa.smartposrestaurant.enums.Role;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 24.10.2022 20:53
 */
@Getter
@Setter
@Entity
@Table(name = "role_permission")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RolePermission extends BaseEntity implements Serializable {

    static final long serialVersionUID = 58L;


    @Column(name = "role")
    @Enumerated(value = EnumType.STRING)
    Role role;

    @Column(name = "permission_code")
    String permissionCode;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(
        name = "permission_code",
        referencedColumnName = "code",
        updatable = false,
        insertable = false,
        foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT)
    )
    Permission permission;

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RolePermission)) {
            return false;
        }
        return id != null && id.equals(((RolePermission) o).id);
    }
}
