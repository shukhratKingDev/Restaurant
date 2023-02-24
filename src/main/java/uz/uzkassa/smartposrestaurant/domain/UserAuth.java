package uz.uzkassa.smartposrestaurant.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import uz.uzkassa.smartposrestaurant.domain.base.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 26.10.2022 15:43
 */
@Getter
@Setter
@Table(name = "user_auth")
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserAuth extends BaseEntity implements Serializable {

    static final long serialVersionUID = 59846L;

    @Column(name = "phone")
    String phone;

    @Column(name = "secret_key")
    String secretKey;

    @Column(name = "created_date")
    LocalDateTime createdDate = LocalDateTime.now();

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserAuth)) {
            return false;
        }
        return id != null && id.equals(((UserAuth) o).id);
    }
}
