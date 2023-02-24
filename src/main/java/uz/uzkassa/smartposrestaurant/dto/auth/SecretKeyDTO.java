package uz.uzkassa.smartposrestaurant.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 26.10.2022 15:12
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SecretKeyDTO implements Serializable {

    private static final long serialVersionUID = 1022426715809722737L;

    String secretKey;
}
