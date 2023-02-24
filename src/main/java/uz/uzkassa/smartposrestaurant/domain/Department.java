package uz.uzkassa.smartposrestaurant.domain;

import java.io.Serializable;
import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import uz.uzkassa.smartposrestaurant.domain.base.AbstractAuditingEntity;
import uz.uzkassa.smartposrestaurant.dto.base.CommonDTO;
import uz.uzkassa.smartposrestaurant.enums.CommonStatus;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 10.10.2022  13:42
 */

@Getter
@Setter
@Entity
@Table(name = "department")
@SQLDelete(sql = "UPDATE department set deleted='false' WHERE id=?")
public class Department extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 8264L;

    @Column(name = "name")
    String name;

    @Column(name = "branch_id")
    String branchId;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id", insertable = false, updatable = false)
    Branch branch;

    @Column(name = "department_status")
    @Enumerated(EnumType.STRING)
    CommonStatus status = CommonStatus.ACTIVE;

    public CommonDTO toCommonDTO() {
        return new CommonDTO(getId(), getName());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Department)) {
            return false;
        }
        return id != null && id.equals(((Department) o).id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
