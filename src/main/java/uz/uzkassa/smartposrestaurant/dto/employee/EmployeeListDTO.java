package uz.uzkassa.smartposrestaurant.dto.employee;

import lombok.Getter;
import lombok.Setter;
import uz.uzkassa.smartposrestaurant.dto.base.CommonDTO;
import uz.uzkassa.smartposrestaurant.enums.Role;
import uz.uzkassa.smartposrestaurant.enums.UserStatus;

import java.io.Serializable;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 13.10.2022 17:28
 */
@Getter
@Setter
public class EmployeeListDTO implements Serializable {

    String id;

    String tin;

    String pinfl;

    String firstName;

    String lastName;

    String patronymic;

    String login;

    Role role;

    UserStatus status;

    CommonDTO branch;

    CommonDTO company;
}
