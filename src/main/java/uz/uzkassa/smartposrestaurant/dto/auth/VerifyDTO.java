package uz.uzkassa.smartposrestaurant.dto.auth;

import lombok.Getter;
import lombok.Setter;
import uz.uzkassa.smartposrestaurant.constants.Constants;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * Powered by: Shuxratjon Rayimjonov
 */
@Getter
@Setter
public class VerifyDTO extends PhoneDTO {

    private static final long serialVersionUID = 1022426715809722737L;

    @NotNull
    @NotEmpty
    @Pattern(regexp = Constants.ACTIVATION_KEY_REGEX)
    String activationKey;
}
