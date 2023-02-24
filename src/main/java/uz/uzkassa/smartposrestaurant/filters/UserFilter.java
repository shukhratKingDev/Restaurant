package uz.uzkassa.smartposrestaurant.filters;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 13.10.2022 17: 32
 */
@Getter
@Setter
public class UserFilter extends BaseFilter {

    String role;

    List<String> roles = new ArrayList<>();

    Boolean validatePinfl;

    @JsonIgnore
    public void addRole(String role) {
        roles.add(role);
    }
}
