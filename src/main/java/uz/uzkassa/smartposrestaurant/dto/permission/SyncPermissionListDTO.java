package uz.uzkassa.smartposrestaurant.dto.permission;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import uz.uzkassa.smartposrestaurant.dto.base.CommonDTO;
import uz.uzkassa.smartposrestaurant.enums.PermissionType;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 25.10.2022 16:15
 */
@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SyncPermissionListDTO extends CommonDTO {

    String code;

    Integer position;

    boolean section;

    PermissionType permissionType;

    SyncPermissionListDTO parent;

}
