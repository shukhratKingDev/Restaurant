package uz.uzkassa.smartposrestaurant.dto.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 10.10.2022 18:25
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TokenDTO implements Serializable {

    @JsonProperty("access_token")
    String accessToken;
}
