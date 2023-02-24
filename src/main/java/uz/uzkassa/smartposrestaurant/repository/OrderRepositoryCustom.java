package uz.uzkassa.smartposrestaurant.repository;

import uz.uzkassa.smartposrestaurant.domain.Order;
import uz.uzkassa.smartposrestaurant.dto.base.ResultList;
import uz.uzkassa.smartposrestaurant.filters.OrderFilter;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 11.25.2022 14:27
 */
public interface OrderRepositoryCustom {
    ResultList<Order> getResultList(OrderFilter filter);
}
