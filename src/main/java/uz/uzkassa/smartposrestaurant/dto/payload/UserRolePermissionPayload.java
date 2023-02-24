package uz.uzkassa.smartposrestaurant.dto.payload;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.List;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 27.10.2022 16:50
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserRolePermissionPayload implements Serializable {

    String userId;

    String role;

    List<String> permissions;
}
