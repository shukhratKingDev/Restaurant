package uz.uzkassa.smartposrestaurant.repository;

import uz.uzkassa.smartposrestaurant.domain.Branch;
import uz.uzkassa.smartposrestaurant.dto.base.ResultList;
import uz.uzkassa.smartposrestaurant.filters.BaseFilter;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 10.10.2022 14:07
 */
public interface BranchRepositoryCustom {
    ResultList<Branch> getResultList(BaseFilter filter);
}
