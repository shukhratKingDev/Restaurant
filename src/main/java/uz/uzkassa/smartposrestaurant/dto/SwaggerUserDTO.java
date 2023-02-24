package uz.uzkassa.smartposrestaurant.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.uzkassa.smartposrestaurant.constants.Constants;
import uz.uzkassa.smartposrestaurant.domain.auth.Authority;
import uz.uzkassa.smartposrestaurant.domain.auth.User;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * User: Shuxratjon Rayimjonov
 * Date: 25.10.2022 20:10
 */
@Getter
@Setter
@NoArgsConstructor
public class SwaggerUserDTO implements Serializable {

    String id;

    @NotBlank
    @Pattern(regexp = Constants.LOGIN_REGEX)
    @Size(min = 1, max = 50)
    String login;

    @Size(max = 50)
    String firstName;

    @Size(max = 50)
    String lastName;

    @Size(max = 256)
    String imageUrl;

    @Size(min = 2, max = 10)
    String langKey;

    String createdBy;

    LocalDateTime createdDate;

    String lastModifiedBy;

    LocalDateTime lastModifiedDate;

    Set<String> authorities;

    public SwaggerUserDTO(User user) {
        this.id = user.getId();
        this.login = user.getLogin();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.authorities = user.getAuthorities().stream().map(Authority::getName).collect(Collectors.toSet());
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SwaggerUserDTO{" +
            "login='" + login + '\'' +
            ", firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", imageUrl='" + imageUrl + '\'' +
            ", langKey='" + langKey + '\'' +
            ", createdBy=" + createdBy +
            ", createdDate=" + createdDate +
            ", lastModifiedBy='" + lastModifiedBy + '\'' +
            ", lastModifiedDate=" + lastModifiedDate +
            ", authorities=" + authorities +
            "}";
    }
}
