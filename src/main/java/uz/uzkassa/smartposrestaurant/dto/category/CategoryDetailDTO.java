package uz.uzkassa.smartposrestaurant.dto.category;

import lombok.Getter;
import lombok.Setter;
import uz.uzkassa.smartposrestaurant.dto.base.CommonDTO;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 24.10.2022 16:57
 */
@Getter
@Setter
public class CategoryDetailDTO extends CommonDTO {

    CommonDTO branch;

    CommonDTO parent;
}
