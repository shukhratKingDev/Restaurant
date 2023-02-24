package uz.uzkassa.smartposrestaurant.dto.address;

import lombok.*;
import lombok.experimental.FieldDefaults;
import uz.uzkassa.smartposrestaurant.dto.base.CommonDTO;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 11.10.2022 17:18
 */
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@ToString
public class AddressDetailDTO {

    CommonDTO region;

    CommonDTO district;

    String street;

    String house;

    String apartment;

    String longitude;

    String latitude;
}
