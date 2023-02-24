package uz.uzkassa.smartposrestaurant.dto.address;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 11.10.2022 18:25
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class AddressDTO implements Serializable {

    String regionId;

    String districtId;

    String street;

    String house;

    String apartment;

    String longitude;

    String latitude;
}
