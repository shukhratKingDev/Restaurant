package uz.uzkassa.smartposrestaurant.repository;

import uz.uzkassa.smartposrestaurant.domain.company.Company;
import uz.uzkassa.smartposrestaurant.dto.base.ResultList;
import uz.uzkassa.smartposrestaurant.filters.CompanyFilter;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 10.10.2022 14:09
 */
public interface CompanyRepositoryCustom {
    ResultList<Company> getResultList(CompanyFilter filter);
}
