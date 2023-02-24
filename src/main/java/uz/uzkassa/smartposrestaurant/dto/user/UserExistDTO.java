package uz.uzkassa.smartposrestaurant.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.uzkassa.smartposrestaurant.enums.UserStatus;

import java.io.Serializable;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 26.10.2022 14:09
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserExistDTO implements Serializable {

    boolean userExist;

    UserStatus userStatus;
}
