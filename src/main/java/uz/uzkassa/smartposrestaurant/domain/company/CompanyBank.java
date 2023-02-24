package uz.uzkassa.smartposrestaurant.domain.company;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.SQLDelete;
import uz.uzkassa.smartposrestaurant.domain.Bank;
import uz.uzkassa.smartposrestaurant.domain.base.BaseEntity;

/**
 * Powered by Shuxratjon Rayimjonov
 * Date: 06.10.2022 18:38
 */

@Entity
@Table(name = "company_bank")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@SQLDelete(sql = "UPDATE company_bank set deleted='true' WHERE id=?")
public class CompanyBank extends BaseEntity implements Serializable {

    static final long serialVersionUID = 10L;

    @Column(name = "bank_id")
    String bankId;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "bank_id", insertable = false, updatable = false)
    Bank bank;

    @Column(name = "account_number")
    String accountNumber;

    @Column(name = "oked")
    String oked;

    @Column(name = "is_main", columnDefinition = "boolean default false")
    boolean main;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CompanyBank)) {
            return false;
        }
        return id != null && id.equals(((CompanyBank) o).id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
