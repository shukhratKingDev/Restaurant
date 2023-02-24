package uz.uzkassa.smartposrestaurant.dto.auth;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 10.10.2022 18:20
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class AdminLoginDTO implements Serializable {
    static final long  serialVersionUID = 1L;

    @NotNull
    @Size(min = 1, max = 50)
    String username;

    @NotNull
    @Size(min = 4, max = 100)
    String password;

    boolean rememberMe;
}
