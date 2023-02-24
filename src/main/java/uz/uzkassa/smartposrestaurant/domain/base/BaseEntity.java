package uz.uzkassa.smartposrestaurant.domain.base;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 05.10.2022
 */

@MappedSuperclass
@Getter
@Setter
public class BaseEntity extends SimpleEntity implements Serializable {

    static final long serialVersionUID = 2L;

    @Column(name = "deleted", columnDefinition = "boolean default false")
    protected boolean deleted = false;
}
