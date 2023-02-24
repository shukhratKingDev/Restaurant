package uz.uzkassa.smartposrestaurant.dto.company;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import uz.uzkassa.smartposrestaurant.dto.activityType.ActivityTypeDTO;
import uz.uzkassa.smartposrestaurant.dto.address.AddressDetailDTO;
import uz.uzkassa.smartposrestaurant.dto.bank.BankDetailDTO;
import uz.uzkassa.smartposrestaurant.dto.company.user.OwnerDTO;
import uz.uzkassa.smartposrestaurant.dto.file.FileDTO;
import uz.uzkassa.smartposrestaurant.enums.BusinessType;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 11.10.2022 17:15
 */
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
public class CompanyDetailDTO extends CompanyBaseDTO {

    BusinessType businessType;

    AddressDetailDTO address;

    FileDTO logo;

    BankDetailDTO bank;

    ActivityTypeDTO activityType;

    OwnerDTO owner;

    String apayId;
}
