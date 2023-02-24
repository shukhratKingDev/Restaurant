package uz.uzkassa.smartposrestaurant.dto.company;

import lombok.*;
import lombok.experimental.FieldDefaults;
import uz.uzkassa.smartposrestaurant.constants.Constants;
import uz.uzkassa.smartposrestaurant.dto.base.CommonDTO;

import javax.validation.constraints.Pattern;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 11.10.2022 16:47
 */
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PROTECTED)
@NoArgsConstructor
@AllArgsConstructor
public class CompanyBaseDTO extends CommonDTO {
    @Pattern(regexp = Constants.TIN_REGEX)
    String tin;

    public CompanyBaseDTO(String id, String name, String tin) {
        this.id = id;
        this.name = name;
        this.tin = tin;
    }
}
