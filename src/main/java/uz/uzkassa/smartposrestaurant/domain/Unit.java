package uz.uzkassa.smartposrestaurant.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.SQLDelete;
import uz.uzkassa.smartposrestaurant.domain.base.LocalizedEntity;
import uz.uzkassa.smartposrestaurant.dto.UnitDTO;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 14.10.2022
 */
@Getter
@Setter
@Entity
@Table(name = "unit")
@SQLDelete(sql = "UPDATE unit SET deleted='true' WHERE id=?")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Unit extends LocalizedEntity implements Serializable {

    public static final String PC = "PC";

    static final long serialVersionUID = 8090L;

    @Column(name = "code")
    String code;

    @Column(name = "measure_id")
    Long measureId;

    @Override
    public String getName() {
        return super.getName();
    }

    public UnitDTO toDTO() {
        return new UnitDTO(getId(), getName(), getCode(), getMeasureId());
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Unit)) {
            return false;
        }
        return id != null && id.equals(((Unit) o).id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
