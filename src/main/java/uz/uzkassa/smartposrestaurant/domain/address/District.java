package uz.uzkassa.smartposrestaurant.domain.address;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import uz.uzkassa.smartposrestaurant.domain.base.LocalizedEntity;
import uz.uzkassa.smartposrestaurant.dto.address.RegionDistrictDTO;

import javax.persistence.*;

/**
 * Powered by Shuxratjon Rayimjonov
 * Date: 06.10.2022 17:03
 */

@Entity
@Table(name = "district")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class District extends LocalizedEntity {

    static final long serialVersionUID = 11L;

    @Column(name = "code")
    Long code;

    @Column(name = "district_id")
    String districtId;

    @Column(name = "region_code")
    Long regionCode;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "region_code", referencedColumnName = "code", updatable = false, insertable = false)
    Region region;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof District)) {
            return false;
        }
        return id != null && id.equals(((District) o).id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    public RegionDistrictDTO toDto() {
        return new RegionDistrictDTO(getId(), getName(), getCode().toString(), getNameRu());
    }

    @Override
    public String getName() {
        return super.getName();
    }

    public RegionDistrictDTO toDTO() {
        return new RegionDistrictDTO(getId(), getName(), getCode().toString(), getNameRu());
    }
}
