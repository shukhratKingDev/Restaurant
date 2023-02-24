package uz.uzkassa.smartposrestaurant.dto.address;

import lombok.*;
import lombok.experimental.FieldDefaults;
import uz.uzkassa.smartposrestaurant.dto.base.CommonLocalizedDTO;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 11.21.2022 19:55
 */
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@ToString
public class AddressLocalizedDTO {

    CommonLocalizedDTO region;

    CommonLocalizedDTO district;

    String street;

    String house;

    String apartment;

    String longitude;

    String latitude;

}
