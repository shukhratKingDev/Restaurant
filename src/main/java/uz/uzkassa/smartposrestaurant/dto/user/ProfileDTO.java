package uz.uzkassa.smartposrestaurant.dto.user;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date:27.10.2022 14:09
 */
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProfileDTO implements Serializable {
    private static final long serialVersionUID = 9122426715809722737L;

    String id;

    String firstName;

    String lastName;

    String patronymic;

    UserCompanyDTO company;

}
