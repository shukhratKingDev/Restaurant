package uz.uzkassa.smartposrestaurant.domain.base;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import uz.uzkassa.smartposrestaurant.domain.auth.User;
import uz.uzkassa.smartposrestaurant.domain.company.Company;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 05.10.2022 17:45
 */

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractAuditingEntity extends SimpleEntity implements Serializable {

    private static final long  serialVersionUID = 1L;

    @Column(name = "deleted", columnDefinition = "boolean default false")
    protected boolean deleted = false;

    @CreatedBy
    @Column(name = "creator_id")
    protected String creatorId;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id", updatable = false, insertable = false)
    protected User creator;

    @LastModifiedBy
    @Column(name = "updater_id")
    protected String updaterId;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "updater_id", updatable = false, insertable = false)
    protected User updater;

    @CreatedDate
    @Column(name = "created_date")
    @JsonIgnore
    protected LocalDateTime createdDate = LocalDateTime.now();

    @LastModifiedDate
    @Column(name = "updated_date")
    @JsonIgnore
    protected LocalDateTime updatedDate = LocalDateTime.now();

    @Column(name = "company_id")
    protected String companyId;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", updatable = false, insertable = false)
    protected Company company;

}
