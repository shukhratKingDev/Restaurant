package uz.uzkassa.smartposrestaurant.dto.address;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.uzkassa.smartposrestaurant.dto.base.CommonCodeDTO;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 11.10.2022 14:52
 */
@Getter
@Setter
@NoArgsConstructor
public class RegionDistrictDTO extends CommonCodeDTO {

    String mapValue;

    public RegionDistrictDTO(String id, String name, String code, String mapValue) {
        super(id, name, code);
        this.mapValue = mapValue;
    }
}
