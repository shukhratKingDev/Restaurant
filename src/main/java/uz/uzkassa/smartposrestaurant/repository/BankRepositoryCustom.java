package uz.uzkassa.smartposrestaurant.repository;

import uz.uzkassa.smartposrestaurant.domain.Bank;
import uz.uzkassa.smartposrestaurant.dto.base.ResultList;
import uz.uzkassa.smartposrestaurant.filters.BaseFilter;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 10.10.2022 14:06
 */
public interface BankRepositoryCustom {
    ResultList<Bank> getResultList(BaseFilter filter);
}
