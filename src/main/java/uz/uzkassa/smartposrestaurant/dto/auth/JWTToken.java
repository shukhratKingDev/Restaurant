package uz.uzkassa.smartposrestaurant.dto.auth;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * Powered by: Rayimjonov Shuxratjon
 * Date: 25.10.2022 20:15
 */
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
public class JWTToken implements Serializable {

    static final long serialVersionUID = 1L;

    @JsonProperty("id_token")
    public String idToken;
}
