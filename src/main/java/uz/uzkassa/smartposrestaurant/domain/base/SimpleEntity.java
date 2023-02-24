package uz.uzkassa.smartposrestaurant.domain.base;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import uz.uzkassa.smartposrestaurant.dto.base.CommonDTO;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.UUID;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 05.10.2022 17:54
 */

@MappedSuperclass
@Getter
@Setter
public abstract class SimpleEntity implements Serializable {

    static final long  serialVersionUID = 3243554667L;

    @Id
//    @GeneratedValue(generator = "uuid")
//    @GenericGenerator(name = "uuid", strategy = "uuid2")
    protected String id;

    public SimpleEntity() {
        id = UUID.randomUUID().toString();
    }

    public String getName() {
        return null;
    }

    public CommonDTO toCommonDTO() {
        return null;
    }
}
