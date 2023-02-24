package uz.uzkassa.smartposrestaurant.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import uz.uzkassa.smartposrestaurant.domain.base.AbstractAuditingEntity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 14.10.2022 18:41
 */
@Entity
@Table(name = "receipt")
@Getter
@Setter
@SQLDelete(sql = "UPDATE receipt SET deleted='true' WHERE id=?")
public class Receipt extends AbstractAuditingEntity implements Serializable {

    static final long serialVersionUID = 2642235L;

    @Column(name = "branch_id")
    String branchId;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id", updatable = false, insertable = false)
    Branch branch;
}
