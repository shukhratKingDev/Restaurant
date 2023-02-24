package uz.uzkassa.smartposrestaurant.dto;

import lombok.Getter;
import lombok.Setter;
import uz.uzkassa.smartposrestaurant.dto.base.CommonCodeDTO;
import uz.uzkassa.smartposrestaurant.dto.base.CommonDTO;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 18.10.2022 17:14
 */
@Getter
@Setter
public class UnitDTO extends CommonCodeDTO {

    Long measureId;

    public UnitDTO(String id, String name, String code, Long measureId) {
        super(id, name, code);
        this.measureId = measureId;
    }
}
