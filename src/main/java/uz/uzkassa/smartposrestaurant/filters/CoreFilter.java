package uz.uzkassa.smartposrestaurant.filters;

import lombok.Getter;
import lombok.Setter;
import uz.uzkassa.smartposrestaurant.enums.SortTypeEnum;

import java.io.Serializable;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 11.10.2022 15:30
 */
@Getter
@Setter
public class CoreFilter implements Serializable {

    protected Integer page = 0;

    protected Integer size = 20;

    protected String search = "";

    protected String orderBy = "id";

    protected SortTypeEnum sortOrder = SortTypeEnum.desc;
}
