package uz.uzkassa.smartposrestaurant.dto.category;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import uz.uzkassa.smartposrestaurant.dto.base.CommonDTO;
import uz.uzkassa.smartposrestaurant.enums.CommonStatus;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 24.10.2022 18:42
 */
@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategoryTreeDTO extends CommonDTO implements Serializable {

    String parentId;

    Long productCount;

    CommonStatus status;

    List<CategoryTreeDTO> children = new ArrayList<>();

    @SuppressWarnings("unused")
    public CategoryTreeDTO(String id, String name, String parentId, BigInteger productCount, String status) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
        this.productCount = productCount != null ? productCount.longValue() : 0L;
        this.status = status != null ? CommonStatus.valueOf(status) : null;
    }

}
