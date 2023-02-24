package uz.uzkassa.smartposrestaurant.dto.department.admin;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import uz.uzkassa.smartposrestaurant.dto.base.CommonDTO;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 14.10.2022 13:58
 */
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
public class DepartmentAdminDTO extends CommonDTO {

    String branchId;
}
