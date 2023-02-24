package uz.uzkassa.smartposrestaurant.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import uz.uzkassa.smartposrestaurant.domain.base.BaseEntity;
import uz.uzkassa.smartposrestaurant.dto.base.CommonDTO;
import uz.uzkassa.smartposrestaurant.dto.category.CategoryTreeDTO;
import uz.uzkassa.smartposrestaurant.enums.CommonStatus;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 14.10.2022 19:42
 */
@SqlResultSetMappings(
    {
        @SqlResultSetMapping(
            name = "CategoryTreeDTOMapper",
            classes = @ConstructorResult(
                targetClass = CategoryTreeDTO.class,
                columns = {
                    @ColumnResult(name = "id", type = Long.class),
                    @ColumnResult(name = "name", type = String.class),
                    @ColumnResult(name = "parentId", type = Long.class),
                    @ColumnResult(name = "productCount", type = BigInteger.class),
                    @ColumnResult(name = "status", type = String.class),
                }
            )
        ),
    }
)
@Getter
@Setter
@Entity
@Table(name = "category")
@SQLDelete(sql = "UPDATE category SET deleted='true' WHERE id=?")
public class Category extends BaseEntity implements Serializable {

    static final long serialVersionUID = 4367L;

    @Column(name = "name")
    String name;

    @Column(name = "catalog_code")
    Long catalogCode;

    @Column(name = "catalog_name")
    String catalogName;

    @Column(name = "cto_id")
    Long ctoId;

    @NotNull
    @Column(name = "branch_id")
    String branchId;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id", insertable = false, updatable = false)
    Branch branch;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    CommonStatus status = CommonStatus.ACTIVE;

    @Column(name = "parent_id")
    String parentId;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id", updatable = false, insertable = false)
    Category parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    @Where(clause = "deleted is not true")
    Set<Category> children = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Category)) {
            return false;
        }
        return id != null && id.equals(((Category) o).id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    public CommonDTO toCommonDTO() {
        return new CommonDTO(getId(), getName());
    }
}
