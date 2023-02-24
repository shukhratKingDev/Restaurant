package uz.uzkassa.smartposrestaurant.domain.permission;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import uz.uzkassa.smartposrestaurant.domain.base.BaseEntity;
import uz.uzkassa.smartposrestaurant.enums.PermissionType;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 14.10.2022 11:14
 */
@Entity
@Table(name = "permission")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Permission extends BaseEntity implements Serializable {

    static final long serialVersionUID = 3894845988L;

    @Column(name = "name")
    String name;

    @Column(name = "code")
    String code;

    @Enumerated(EnumType.STRING)
    @Column(name = "permission_type")
    PermissionType permissionType;

    @Column(name = "position")
    Integer position;

    @Column(name = "section", columnDefinition = "boolean default false")
    boolean section = false;

    @Column(name = "parent_Id")
    String parentId;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id", updatable = false, insertable = false)
    Permission parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    Set<Permission> children = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Permission)) {
            return false;
        }
        return id != null && id.equals(((Permission) o).id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
