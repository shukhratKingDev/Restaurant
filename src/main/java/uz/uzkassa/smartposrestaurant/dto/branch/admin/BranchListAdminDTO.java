package uz.uzkassa.smartposrestaurant.dto.branch.admin;

import static lombok.AccessLevel.*;

import java.io.Serializable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import uz.uzkassa.smartposrestaurant.dto.address.AddressDetailDTO;
import uz.uzkassa.smartposrestaurant.dto.base.CommonDTO;
import uz.uzkassa.smartposrestaurant.dto.company.CompanyBaseDTO;
import uz.uzkassa.smartposrestaurant.dto.company.user.OwnerDTO;
import uz.uzkassa.smartposrestaurant.enums.CommonStatus;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 12.10.2022 14:50
 */
@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = PRIVATE)
public class BranchListAdminDTO extends CommonDTO {

    CommonStatus status;

    AddressDetailDTO address;

    OwnerDTO owner;

    CompanyBaseDTO company;
}
