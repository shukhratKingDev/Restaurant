package uz.uzkassa.smartposrestaurant.repository;

import uz.uzkassa.smartposrestaurant.domain.Category;
import uz.uzkassa.smartposrestaurant.dto.base.ResultList;
import uz.uzkassa.smartposrestaurant.dto.category.CategoryTreeDTO;
import uz.uzkassa.smartposrestaurant.filters.BaseFilter;

import java.util.List;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 18.10.2022 16:21
 */
public interface CategoryRepositoryCustom {
    ResultList<Category> getResultList(BaseFilter filter);

    List<CategoryTreeDTO> treeList(BaseFilter filter);
}
