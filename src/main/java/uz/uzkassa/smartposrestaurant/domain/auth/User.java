package uz.uzkassa.smartposrestaurant.domain.auth;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.SQLDelete;
import uz.uzkassa.smartposrestaurant.constants.Constants;
import uz.uzkassa.smartposrestaurant.domain.Branch;
import uz.uzkassa.smartposrestaurant.domain.FileUpload;
import uz.uzkassa.smartposrestaurant.domain.address.Address;
import uz.uzkassa.smartposrestaurant.domain.base.AbstractAuditingEntity;
import uz.uzkassa.smartposrestaurant.dto.base.CommonDTO;
import uz.uzkassa.smartposrestaurant.enums.LanguageEnum;
import uz.uzkassa.smartposrestaurant.enums.Stage;
import uz.uzkassa.smartposrestaurant.enums.UserStatus;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 05.10.2022 18:27
 */

@Getter
@Setter
@Entity
@Table(name = "users")
@SQLDelete(sql = "UPDATE users set deleted='true' where id=?")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class User extends AbstractAuditingEntity implements Serializable {

    static final long serialVersionUID = 16L;

    @NotNull
    @Pattern(regexp = Constants.LOGIN_REGEX)
    @Size(min = 1, max = 50)
    @Column(length = 50, nullable = false)
    String login;

    @JsonIgnore
    @NotNull
    @Size(min = 60, max = 60)
    @Column(name = "password_hash", length = 60, nullable = false)
    String password;

    @Size(max = 50)
    @Column(name = "first_name", length = 50)
    String firstName;

    @Size(max = 50)
    @Column(name = "last_name", length = 50)
    String lastName;

    @Size(max = 50)
    @Column(name = "patronymic", length = 50)
    String patronymic;

    @Size(min = 9, max = 9)
    @Column(name = "tin")
    String tin;

    @Size(min = 14, max = 14)
    @Column(name = "pinfl")
    String pinfl;

    @Embedded
    Address address;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    UserStatus status = UserStatus.PENDING;

    @Enumerated(EnumType.STRING)
    @Column(name = "language")
    LanguageEnum language = LanguageEnum.ru;

    @Enumerated(EnumType.STRING)
    @Column(name = "stage")
    Stage stage = Stage.PARTIAL_SIGNED;

    @Column(name = "activation_key")
    @JsonIgnore
    String activationKey;

    @Size(max = 20)
    @Column(name = "reset_key", length = 20)
    @JsonIgnore
    String resetKey;

    @Column(name = "reset_date")
    LocalDateTime resetDate = null;

    @Column(name = "branch_id")
    String branchId;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id", insertable = false, updatable = false)
    Branch branch;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
        name = "user_authority",
        joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
        inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "name")}
    )

    @BatchSize(size = 20)
    Set<Authority> authorities = new HashSet<>();

    @Size(max = 12, min = 12)
    @Pattern(regexp = Constants.PHONE_REGEX)
    @Column(name = "phone", length = 12)
    String phone;

    @Column(name = "profile_photo_id")
    String profilePhotoId;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_photo_id", insertable = false, updatable = false)
    FileUpload profilePhoto;

    @Override
    public String getName() {
        StringBuilder name = new StringBuilder();
        if (getFirstName() != null) {
            name.append(getFirstName()).append(" ");
        }
        if (getLastName() != null) {
            name.append(getLastName());
        }
        return name.toString().trim();
    }

    @Override
    public CommonDTO toCommonDTO() {
        return new CommonDTO(getId(), getName());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof User)) {
            return false;
        }
        return id != null && id.equals(((User) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }


    @Override
    public String toString() {
        return "User{" +
            "login='" + login + '\'' +
            ", firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", patronymic='" + patronymic + '\'' +
            '}';
    }
}
