package uz.uzkassa.smartposrestaurant.dto.auth;

import lombok.Getter;
import lombok.Setter;
import uz.uzkassa.smartposrestaurant.constants.Constants;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 26.10.2022 19:40
 */
@Getter
@Setter
public class UpdateLoginDTO extends LoginDTO implements Serializable {

    @NotNull
    @NotEmpty
    @Pattern(regexp = Constants.ACTIVATION_KEY_REGEX)
    String activationKey;

    @NotNull
    @NotEmpty
    @Size(min = 36, max = 36)
    String secretKey;
}
