package uz.uzkassa.smartposrestaurant.repository.impl;

import uz.uzkassa.smartposrestaurant.domain.Order;
import uz.uzkassa.smartposrestaurant.dto.base.ResultList;
import uz.uzkassa.smartposrestaurant.enums.OrderStatus;
import uz.uzkassa.smartposrestaurant.filters.OrderFilter;
import uz.uzkassa.smartposrestaurant.repository.OrderRepositoryCustom;
import uz.uzkassa.smartposrestaurant.utils.SecurityUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Optional;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 11.25.2022 13:29
 */
public class OrderRepositoryCustomImpl implements OrderRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public ResultList<Order> getResultList(OrderFilter filter) {
        String companyId = Optional.ofNullable(filter.getCompanyId()).orElse(SecurityUtils.getCurrentCompanyId());

        ResultList<Order> resultList = new ResultList<>();
        StringBuilder sql = new StringBuilder();
        sql.append("select o from Order o");
        sql.append(" where o.deleted is not true");

        if (companyId != null) {
            sql.append(" and o.companyId=:companyId");
        }
        if (filter.getUserId() != null) {
            sql.append(" and o.userId=:userId ");
        }
        if (filter.getStatus() != null) {
            sql.append(" and o.status=:status");
        }
        if (filter.getFrom() != null) {
            sql.append(" and o.orderDate=>:fromDate");
        }
        if (filter.getTo() != null) {
            sql.append(" and o.orderDate<=:toDate");
        }
        if (filter.isNotEmpty()) {
            sql.append(" and (");
            sql.append(" lower(o.orderNumber)").append(filter.getLikeSearchKey());
            sql.append(") ");
        }
        String countSql = sql.toString().replaceFirst("select o", "select count(o.id)");
        sql.append("order by o.").append(filter.getOrderBy());
        sql.append(" ").append(filter.getSortOrder().getName());

        TypedQuery<Order> query = entityManager
            .createQuery(sql.toString(), Order.class)
            .setFirstResult(filter.getStart())
            .setMaxResults(filter.getSize());

        TypedQuery<Long> countQuery = entityManager.createQuery(countSql, Long.class);
        if (companyId != null) {
            query.setParameter("companyId", companyId);
            countQuery.setParameter("companyId", companyId);
        }
        if (filter.getUserId() != null) {
            query.setParameter("userId", filter.getUserId());
            countQuery.setParameter("userId", filter.getUserId());
        }
        if (filter.getStatus() != null) {
            query.setParameter("status", OrderStatus.valueOf(filter.getStatus()));
            countQuery.setParameter("status", OrderStatus.valueOf(filter.getStatus()));
        }
        if (filter.getFrom() != null) {
            query.setParameter("fromDate", filter.getFrom());
            countQuery.setParameter("fromDate", filter.getFrom());
        }
        if (filter.getTo() != null) {
            query.setParameter("toDate", filter.getTo());
            countQuery.setParameter("toDate", filter.getTo());
        }
        if (filter.isNotEmpty()) {
            query.setParameter("searchKey", filter.getSearchForQuery());
            countQuery.setParameter("searchKey", filter.getSearchForQuery());
        }

        Long count = countQuery.getSingleResult();
        if (count > 0) {
            resultList.setList(query.getResultList());
            resultList.setCount(count);
        }
        return resultList;
    }
}
