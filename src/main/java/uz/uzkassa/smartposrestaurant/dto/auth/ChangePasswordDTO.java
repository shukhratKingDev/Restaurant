package uz.uzkassa.smartposrestaurant.dto.auth;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 14.10.2022 11:54
 */
@Getter
@Setter
@NoArgsConstructor
public class ChangePasswordDTO implements Serializable {

    String currentPassword;

    String newPassword;
}
