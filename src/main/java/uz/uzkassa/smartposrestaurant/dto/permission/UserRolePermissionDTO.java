package uz.uzkassa.smartposrestaurant.dto.permission;

import lombok.*;
import lombok.experimental.FieldDefaults;
import uz.uzkassa.smartposrestaurant.enums.PermissionType;

import java.io.Serializable;
import java.util.List;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 25.10.2022 14:15
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserRolePermissionDTO implements Serializable {

    List<String> permissions;

    PermissionType permissionType;
}
