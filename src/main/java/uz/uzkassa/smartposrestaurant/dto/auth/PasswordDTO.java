package uz.uzkassa.smartposrestaurant.dto.auth;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 26.10.2022 18:59
 */
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PasswordDTO implements Serializable {

    static final long serialVersionUID = 845724546L;

    @NotNull
    @NotEmpty
    @Size(min = 4, max = 50)
    String password;

    @NotNull
    @NotEmpty
    @Size(min = 4, max = 50)
    String passwordConfirmation;


    @NotNull
    @NotEmpty
    @Size(min = 6, max = 6)
    String activationKey;


    @NotNull
    @NotEmpty
    @Size(min = 36, max = 36)
    String secretKey;


}
