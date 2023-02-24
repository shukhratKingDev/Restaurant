package uz.uzkassa.smartposrestaurant.domain.address;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import uz.uzkassa.smartposrestaurant.domain.base.LocalizedEntity;
import uz.uzkassa.smartposrestaurant.dto.address.RegionDistrictDTO;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 06.10.2022
 */

@Entity
@Table(name = "region")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Region extends LocalizedEntity implements Serializable {

    static final long serialVersionUID = 14L;

    @NotNull
    @NotEmpty
    @Column(name = "code", unique = true)
    Long code;

    @Column(name = "chart_code")
    String chartCode;

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Region)) {
            return false;
        }
        return id != null && id.equals(((Region) o).id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    public RegionDistrictDTO toDTO() {
        return new RegionDistrictDTO(getId(), getName(), getCode().toString(), getNameRu());
    }
}
