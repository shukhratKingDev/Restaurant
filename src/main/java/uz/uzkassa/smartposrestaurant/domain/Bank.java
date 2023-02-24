package uz.uzkassa.smartposrestaurant.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.SQLDelete;
import uz.uzkassa.smartposrestaurant.domain.base.LocalizedEntity;
import uz.uzkassa.smartposrestaurant.dto.bank.BankLookUpDTO;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 06.10.2022 18:30
 */

@Getter
@Setter
@Table(name = "bank")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Entity
@SQLDelete(sql = "UPDATE bank set deleted='false' WHERE id=?")
public class Bank extends LocalizedEntity implements Serializable {

    static final long  serialVersionUID = 7L;

    @Size(min = 2, max = 2)
    @Column(name = "code", length = 2)
    String code;

    @Column(name = "mfo")
    String mfo;

    @Column(name = "tin")
    String tin;

    @Column(name = "parent_id")
    String parentId;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id", insertable = false, updatable = false)
    Bank parent;


    public BankLookUpDTO toLookUpDto() {
        return new BankLookUpDTO(getId(), getName(), getMfo());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Bank)) {
            return false;
        }
        return id != null && id.equals(((Bank) o).id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
