package uz.uzkassa.smartposrestaurant.dto.company.user;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 11.10.2022 16:53
 */
@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OwnerDTO implements Serializable {

    String id;

    String firstName;

    String lastName;

    String patronymic;

    String login;

    String phone;

    String tin;

    String pinfl;
}
