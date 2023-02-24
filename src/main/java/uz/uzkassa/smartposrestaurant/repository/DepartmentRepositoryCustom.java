package uz.uzkassa.smartposrestaurant.repository;

import uz.uzkassa.smartposrestaurant.domain.Department;
import uz.uzkassa.smartposrestaurant.dto.base.ResultList;
import uz.uzkassa.smartposrestaurant.filters.BaseFilter;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 10.10.2022 14: 14
 */
public interface DepartmentRepositoryCustom {
    ResultList<Department> getResultList(BaseFilter filter);
}
