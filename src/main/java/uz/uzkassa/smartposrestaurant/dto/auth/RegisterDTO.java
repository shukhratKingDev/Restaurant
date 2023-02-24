package uz.uzkassa.smartposrestaurant.dto.auth;

import lombok.Getter;
import lombok.Setter;
import uz.uzkassa.smartposrestaurant.constants.Constants;
import uz.uzkassa.smartposrestaurant.enums.LanguageEnum;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 26.10.2022 16:36
 */
@Getter
@Setter
public class RegisterDTO implements Serializable {

    private static final long serialVersionUID = 9112426715809722737L;

    @NotNull
    @NotEmpty
    @Pattern(regexp = Constants.PHONE_REGEX)
    String phone;

    @NotNull
    @NotEmpty
    @Size(min = 4, max = 50)
    String password;

    @NotNull
    @NotEmpty
    @Size(min = 4, max = 50)
    String passwordConfirmation;

    @NotNull
    @Size(min = 36, max = 36)
    String secretKey;

    LanguageEnum language;

}
