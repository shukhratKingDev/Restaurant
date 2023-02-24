package uz.uzkassa.smartposrestaurant.dto.employee;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 13.10.2022 15:36
 */
@Getter
@Setter
public class EmployeeDTO implements Serializable {

    String id;

    String tin;

    String pinfl;

    String firstName;

    String lastName;

    String patronymic;

    String login;

    String password;

    String phone;

    String branch;

    String companyId;

    String role;
}
