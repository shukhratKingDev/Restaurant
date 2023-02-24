package uz.uzkassa.smartposrestaurant.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import uz.uzkassa.smartposrestaurant.domain.base.LocalizedEntity;
import uz.uzkassa.smartposrestaurant.dto.base.CommonDTO;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Powered by Shuxratjon Rayimjonov
 * Date: 06.10.2022 17:53
 */

@Entity
@Table(name = "activity_type")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Getter
@Setter
public class ActivityType extends LocalizedEntity {

    static final long  serialVersionUID = 4L;

    @Column(name = "cto_id", unique = true)
    Long ctoId;

    @NotNull
    @Column(name = "code")
    Long code;

    @Column(name = "parent_id")
    String parentId;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id", insertable = false, updatable = false)
    ActivityType activityType;


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ActivityType)) {
            return false;
        }
        return id != null && id.equals(((ActivityType) o).id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    public CommonDTO toCommonDto() {
        return new CommonDTO(getId(), getName());
    }
}
