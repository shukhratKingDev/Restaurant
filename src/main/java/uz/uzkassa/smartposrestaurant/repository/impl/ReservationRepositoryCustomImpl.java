package uz.uzkassa.smartposrestaurant.repository.impl;

import org.springframework.util.CollectionUtils;
import uz.uzkassa.smartposrestaurant.domain.Reservation;
import uz.uzkassa.smartposrestaurant.dto.base.ResultList;
import uz.uzkassa.smartposrestaurant.filters.ReservationFilter;
import uz.uzkassa.smartposrestaurant.repository.ReservationRepositoryCustom;
import uz.uzkassa.smartposrestaurant.utils.SecurityUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Optional;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 11.29.2022 16:29
 */
public class ReservationRepositoryCustomImpl implements ReservationRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public ResultList<Reservation> getResultList(ReservationFilter filter) {
        String branchId = Optional.ofNullable(filter.getBranchId()).orElse(SecurityUtils.getCurrentBranchId());
        ResultList<Reservation> resultList = new ResultList<>();
        StringBuilder sql = new StringBuilder();
        sql.append("select r from Reservation r ");
        sql.append(" join r.client c ");
        sql.append(" left join r.order o ");
        sql.append(" where r.deleted is not true ");
        sql.append(" and r.branchId=:branchId");
        if (filter.getClientId() != null) {
            sql.append(" and r.clientId=:clientId");
        }
        if (filter.getFrom() != null) {
            sql.append(" and r.reservationDate=>:fromDate");
        }
        if (filter.getTo() != null) {
            sql.append(" and r.reservationDate<=:toDate");
        }
        if (filter.isNotEmpty()) {
            sql.append(" and(");
            sql.append(" lower(c.phone").append(filter.getLikeSearchKey());
            sql.append(" or lower(c.login").append(filter.getLikeSearchKey());
            sql.append(" or lower(c.firstName").append(filter.getLikeSearchKey());
            sql.append(" or lower(c.lastName").append(filter.getLikeSearchKey());
            sql.append(" or lower(o.orderNumber").append(filter.getLikeSearchKey());
            sql.append(" )");
        }
        String countSql = sql.toString().replaceFirst("select r", "select count(r.id)");
        sql.append(" order by r.").append(filter.getOrderBy());
        sql.append(" ").append(filter.getSortOrder().getName());

        TypedQuery<Reservation> query = entityManager
            .createQuery(sql.toString(), Reservation.class)
            .setFirstResult(filter.getStart())
            .setMaxResults(filter.getSize());

        TypedQuery<Long> countQuery = entityManager.createQuery(countSql, Long.class);

        if (branchId != null) {
            query.setParameter("branchId", branchId);
            countQuery.setParameter("branchId", branchId);
        }
        if (filter.getClientId() != null) {
            query.setParameter("clientId", filter.getClientId());
            countQuery.setParameter("clientId", filter.getClientId());
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
