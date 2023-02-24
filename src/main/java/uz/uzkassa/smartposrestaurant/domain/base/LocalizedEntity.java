package uz.uzkassa.smartposrestaurant.domain.base;

import lombok.Getter;
import lombok.Setter;
import uz.uzkassa.smartposrestaurant.utils.SecurityUtils;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 05.10.2022 18:16
 */

@MappedSuperclass
@Getter
@Setter
public class LocalizedEntity extends BaseEntity implements Serializable {

    static final long  serialVersionUID = 3L;

    @Column(name = "name_uz")
    protected String nameUz;

    @Column(name = "name_cyrillic")
    protected String nameCyrillic;

    @Column(name = "name_ru")
    protected String nameRu;

    public String getName() {
        switch (SecurityUtils.getCurrentRequestLanguageEnum()) {
            case uz:
                return nameUz;
            case cyrillic:
                return nameCyrillic;
            default:
                return nameRu;
        }
    }
}
