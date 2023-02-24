package uz.uzkassa.smartposrestaurant.dto.permission;

import lombok.Getter;
import lombok.Setter;
import uz.uzkassa.smartposrestaurant.dto.base.CommonDTO;
import uz.uzkassa.smartposrestaurant.enums.PermissionType;

import java.io.Serializable;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 24.10.2022 19:48
 */
@Getter
@Setter
public class PermissionDTO extends CommonDTO implements Serializable {

    String code;

    Integer position;

    boolean section;

    String parentId;

    PermissionType permissionType;
}
