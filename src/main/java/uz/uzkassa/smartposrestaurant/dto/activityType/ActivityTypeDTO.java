package uz.uzkassa.smartposrestaurant.dto.activityType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.uzkassa.smartposrestaurant.dto.base.CommonDTO;

import java.io.Serializable;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 11.10.2022 17:27
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ActivityTypeDTO extends CommonDTO implements Serializable {

    String parentId;

    String code;

    ActivityTypeDTO parent;

    public ActivityTypeDTO(String id, String name, String parentId) {
        super(id, name);
        this.parentId = parentId;
    }
}
