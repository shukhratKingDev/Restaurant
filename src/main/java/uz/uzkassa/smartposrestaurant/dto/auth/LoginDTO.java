package uz.uzkassa.smartposrestaurant.dto.auth;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 26.10.2022 19:29
 */
@Getter
@Setter
public class LoginDTO extends PhoneDTO implements Serializable {

    private static final long serialVersionUID = 303242671681722737L;

    @NotNull
    @Size(max = 50, min = 4)
    String password;

    boolean rememberMe;
}
