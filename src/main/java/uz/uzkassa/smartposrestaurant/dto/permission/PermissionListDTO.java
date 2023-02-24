package uz.uzkassa.smartposrestaurant.dto.permission;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 24.10.2022 20:29
 */
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PermissionListDTO extends PermissionDTO implements Serializable {

    List<PermissionListDTO> children = new ArrayList<>();
}
