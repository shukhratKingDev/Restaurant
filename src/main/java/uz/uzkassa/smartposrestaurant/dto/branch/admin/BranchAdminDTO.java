package uz.uzkassa.smartposrestaurant.dto.branch.admin;

import static lombok.AccessLevel.PRIVATE;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import uz.uzkassa.smartposrestaurant.dto.address.AddressDTO;
import uz.uzkassa.smartposrestaurant.dto.base.CommonDTO;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 12.10.2022 11:49
 */
@Setter
@Getter
@FieldDefaults(level = PRIVATE)
@NoArgsConstructor
public class BranchAdminDTO extends CommonDTO {

    AddressDTO address;

    String ownerId;

    String companyId;
}
