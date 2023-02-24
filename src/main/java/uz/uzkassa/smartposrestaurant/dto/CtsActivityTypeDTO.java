package uz.uzkassa.smartposrestaurant.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import uz.uzkassa.smartposrestaurant.dto.base.CommonLocalizedDTO;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 11.21.2022 19:44
 */
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CtsActivityTypeDTO extends CommonLocalizedDTO {

    CommonLocalizedDTO parent;
}
