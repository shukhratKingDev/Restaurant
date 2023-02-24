package uz.uzkassa.smartposrestaurant.dto.branch.admin;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import uz.uzkassa.smartposrestaurant.dto.address.AddressDetailDTO;
import uz.uzkassa.smartposrestaurant.dto.base.CommonDTO;
import uz.uzkassa.smartposrestaurant.dto.company.user.OwnerDTO;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 12.10.2022 16:25
 */

@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BranchDetailAdminDTO extends CommonDTO {

    AddressDetailDTO address;

    OwnerDTO owner;

    CommonDTO company;
}
