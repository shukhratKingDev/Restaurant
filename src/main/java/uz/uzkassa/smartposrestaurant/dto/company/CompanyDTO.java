package uz.uzkassa.smartposrestaurant.dto.company;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import uz.uzkassa.smartposrestaurant.dto.activityType.ActivityTypeDTO;
import uz.uzkassa.smartposrestaurant.dto.address.AddressDTO;
import uz.uzkassa.smartposrestaurant.enums.BusinessType;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 11.10.2022 18:47
 */
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
public class CompanyDTO extends CompanyBaseDTO {

    BusinessType businessType;

    AddressDTO address;

    ActivityTypeDTO activityType;
}
