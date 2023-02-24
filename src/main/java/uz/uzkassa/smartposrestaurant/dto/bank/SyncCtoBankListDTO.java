package uz.uzkassa.smartposrestaurant.dto.bank;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 10.10.2022  17:57
 */
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
public class SyncCtoBankListDTO implements Serializable {
    String id;

    String name;

    String code;

    String tin;

    String parentCode;

    String categoryCode;

    String regionCode;

    String districtCode;

    String street;

    String house;

    String apartment;

}
