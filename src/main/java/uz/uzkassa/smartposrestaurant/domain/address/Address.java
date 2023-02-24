package uz.uzkassa.smartposrestaurant.domain.address;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 06.10.2022 16:49
 */

@Getter
@Setter
@EqualsAndHashCode
@Embeddable
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Address implements Serializable {

    static final long serialVersionUID = 5L;

    @Column(name = "street")
    String street;

    @Column(name = "house")
    String house;

    @Column(name = "apartment")
    String apartment;

    @Column(name = "longitude")
    String longitude;

    @Column(name = "latitude")
    String latitude;

    @Column(name = "region_id")
    String regionId;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id", updatable = false, insertable = false)
    Region region;

    @Column(name = "district_id")
    String districtId;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "district_id", updatable = false, insertable = false)
    District district;

}
