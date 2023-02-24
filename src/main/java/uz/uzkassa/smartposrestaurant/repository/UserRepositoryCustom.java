package uz.uzkassa.smartposrestaurant.repository;

import uz.uzkassa.smartposrestaurant.domain.auth.User;
import uz.uzkassa.smartposrestaurant.dto.base.ResultList;
import uz.uzkassa.smartposrestaurant.filters.UserFilter;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 10.10.2022 14:09
 */
public interface UserRepositoryCustom {
    ResultList<User> getResultList(UserFilter filter);
}
