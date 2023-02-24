package uz.uzkassa.smartposrestaurant.dto.category;

import lombok.Getter;
import lombok.Setter;
import uz.uzkassa.smartposrestaurant.dto.base.CommonDTO;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 24.10.2022 17: 06
 */
@Getter
@Setter
public class CategoryListDTO extends CommonDTO {

    CommonDTO branch;

    CommonDTO parent;
}
