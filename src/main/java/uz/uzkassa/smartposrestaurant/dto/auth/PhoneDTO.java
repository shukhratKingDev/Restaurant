package uz.uzkassa.smartposrestaurant.dto.auth;

import lombok.Getter;
import lombok.Setter;
import uz.uzkassa.smartposrestaurant.constants.Constants;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 26.10.2022 14:11
 */
@Getter
@Setter
public class PhoneDTO implements Serializable {

    private static final long serialVersionUID = 1012426715809722737L;

    @NotNull
    @NotEmpty
    @Pattern(regexp = Constants.PHONE_REGEX)
    String phone;
}
