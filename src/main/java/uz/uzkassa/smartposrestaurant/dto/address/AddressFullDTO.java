package uz.uzkassa.smartposrestaurant.dto.address;

import lombok.*;
import lombok.experimental.FieldDefaults;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 11.21.2022 19:16
 */
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@ToString
public class AddressFullDTO {

    RegionDistrictDTO region;

    RegionDistrictDTO district;

    String street;

    String house;

    String office;

    String apartment;

    String longitude;

    String latitude;
}
