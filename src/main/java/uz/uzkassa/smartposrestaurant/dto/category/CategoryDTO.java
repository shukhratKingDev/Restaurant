package uz.uzkassa.smartposrestaurant.dto.category;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.uzkassa.smartposrestaurant.dto.base.CommonDTO;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 24.10.2022 16:33
 */
@Getter
@Setter
@NoArgsConstructor
public class CategoryDTO extends CommonDTO {

    String branchId;

    String parentId;

}
