package uz.uzkassa.smartposrestaurant.dto.cts;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.uzkassa.smartposrestaurant.dto.activityType.ActivityTypeDTO;
import uz.uzkassa.smartposrestaurant.dto.address.AddressFullDTO;

import java.io.Serializable;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 11.21.2022 19: 24
 */
@Getter
@Setter
@NoArgsConstructor
public class CtsMerchantDTO implements Serializable {

    Long id;

    String apayMerchantId;

    String name;

    String phone;

    AddressFullDTO address;

    ActivityTypeDTO activityType;
}
