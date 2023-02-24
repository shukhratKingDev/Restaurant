package uz.uzkassa.smartposrestaurant.domain.base;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 05.10.2022 18:10
 */

@MappedSuperclass
@Getter
@Setter
public class LocalizedAuditingEntity extends AbstractAuditingEntity implements Serializable {

    static final long  serialVersionUID = 475457568L;

    @Column(name = "name_uz")
    protected String nameUz;

    @Column(name = "name_cyrillic")
    protected String nameCyrillic;

    @Column(name = "name_ru")
    protected String nameRu;

    @Override
    public String getId() {
        return id;
    }
}
