package uz.uzkassa.smartposrestaurant.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 07.10.2022 19:28
 */
@Getter
@Setter
public class UserAuth extends User {

    private String userId;
    private String companyId;
    private String branchId;
    private String language;
    private String tin;

    public UserAuth(
        String username,
        String password,
        Collection<? extends GrantedAuthority> authorities,
        String userId,
        String companyId,
        String branchId,
        String language,
        String tin
    ) {
        super(username, password, authorities);
        this.userId = userId;
        this.companyId = companyId;
        this.branchId = branchId;
        this.language = language;
        this.tin = tin;
    }

}
