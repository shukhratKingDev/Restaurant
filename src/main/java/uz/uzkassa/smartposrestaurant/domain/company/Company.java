package uz.uzkassa.smartposrestaurant.domain.company;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.SQLDelete;
import uz.uzkassa.smartposrestaurant.constants.Constants;
import uz.uzkassa.smartposrestaurant.domain.ActivityType;
import uz.uzkassa.smartposrestaurant.domain.FileUpload;
import uz.uzkassa.smartposrestaurant.domain.address.Address;
import uz.uzkassa.smartposrestaurant.domain.auth.User;
import uz.uzkassa.smartposrestaurant.domain.base.AbstractAuditingEntity;
import uz.uzkassa.smartposrestaurant.dto.base.CommonDTO;
import uz.uzkassa.smartposrestaurant.dto.company.CompanyBaseDTO;
import uz.uzkassa.smartposrestaurant.enums.BusinessType;
import uz.uzkassa.smartposrestaurant.enums.CommonStatus;
import uz.uzkassa.smartposrestaurant.enums.Vat;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Powered by Shuxratjon Rayimjonov
 * Date: 06.10.2022 16:32
 */

@Getter
@Setter
@Entity
@Table(name = "company")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@SQLDelete(sql = "UPDATE company set deleted='true' where id=?")
public class Company extends AbstractAuditingEntity implements Serializable {

    static final long serialVersionUID = 9L;

    @Column(name = "name")
    String name;

    @Column(name = "brand")
    String brand;

    @Column(name = "apay_id")
    String apayId;

    @Column(name = "tin", unique = true, length = 14)
    @Pattern(regexp = Constants.TIN_REGEX)
    String tin;

    @Enumerated(EnumType.STRING)
    @Column(name = "business_type")
    BusinessType businessType;

    @Column(name = "vat")
    @Enumerated(value = EnumType.STRING)
    Vat vat;

    @Embedded
    Address address;

    @Column(name = "owner_id")
    String ownerId;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", updatable = false, insertable = false)
    User owner;

    @Column(name = "logo_id")
    String logoId;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "logo_id", insertable = false, updatable = false)
    FileUpload logo;

    @Column(name = "registration_date")
    LocalDateTime registrationDate;

    @Column(name = "activity_type_id")
    String activityTypeId;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "activity_type_id", insertable = false, updatable = false)
    ActivityType activityType;

    @Column(name = "company_bank_id")
    String companyBankId;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "company_bank_id", insertable = false, updatable = false)
    CompanyBank companyBank;

    @Column(name = "company_status")
    @Enumerated(EnumType.STRING)
    CommonStatus status = CommonStatus.ACTIVE;


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Company)) {
            return false;
        }
        return id != null && id.equals(((Company) o).id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    public CommonDTO toCommonDTO() {
        return new CommonDTO(getId(), getName());
    }
    public CompanyBaseDTO toCompanyBaseDTO(){
        return new CompanyBaseDTO(getId(),getName(),getTin());
    }
}
