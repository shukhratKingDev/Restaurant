package uz.uzkassa.smartposrestaurant.repository.impl;

import org.springframework.util.CollectionUtils;
import uz.uzkassa.smartposrestaurant.domain.auth.User;
import uz.uzkassa.smartposrestaurant.dto.base.ResultList;
import uz.uzkassa.smartposrestaurant.enums.UserStatus;
import uz.uzkassa.smartposrestaurant.filters.UserFilter;
import uz.uzkassa.smartposrestaurant.repository.UserRepositoryCustom;
import uz.uzkassa.smartposrestaurant.utils.SecurityUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Optional;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 10.10.2022 14:21
 */
public class UserRepositoryImpl implements UserRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public ResultList<User> getResultList(UserFilter filter) {
        String companyId = Optional.ofNullable(filter.getCompanyId()).orElse(SecurityUtils.getCurrentCompanyId());
        ResultList<User> resultList = new ResultList<>();
        StringBuilder sql = new StringBuilder();
        sql.append("select distinct u from User u \n");
        sql.append(" inner join u.authorities ua \n");
        sql.append(" inner join u.userBranches b ");
        sql.append(" where u.deleted is not true \n");
        if (companyId != null) {
            sql.append(" and u.companyId=:companyId \n");
        }
        if (filter.getBranchId() != null) {
            sql.append(" and b.id=:branchId");
        }
        if (filter.getValidatePinfl() != null && filter.getValidatePinfl()) {
            sql.append(" and u.pinfl is not null ");
        }
        if (filter.getRole() != null) {
            sql.append(" and ua.name=:role");
        } else if (!CollectionUtils.isEmpty(filter.getRoles())) {
            sql.append(" and ua.name in (:roles)");
        }
        if (filter.getStatus() != null) {
            sql.append(" and u.status=:status \n");
        }
        if (filter.getFrom() != null) {
            sql.append(" and u.createdDate>=:fromDate");
        }
        if (filter.getTo() != null) {
            sql.append(" and u.createdDate<=:toDate");
        }
        if (filter.isNotEmpty()) {
            sql.append(" and (");
            sql.append(" lower(u.firstName)").append(filter.getLikeSearchKey());
            sql.append(" or lower(u.lastName)").append(filter.getLikeSearchKey());
            sql.append(" or lower(u.patronymic)").append(filter.getLikeSearchKey());
            sql.append(" or lower(u.login)").append(filter.getLikeSearchKey());
            sql.append(")");
        }

        String countSql = sql.toString().replaceFirst("select distinct u", "select count(distinct u.id)");
        sql.append(" order by u.").append(filter.getDefaultOrderBy());
        sql.append(" ").append(filter.getSortOrder().getName());

        TypedQuery<User> query = entityManager
            .createQuery(sql.toString(), User.class)
            .setFirstResult(filter.getStart())
            .setMaxResults(filter.getSize());

        TypedQuery<Long> countQuery = entityManager.createQuery(countSql, Long.class);

        if (companyId != null) {
            query.setParameter("companyId", companyId);
            countQuery.setParameter("companyId", companyId);
        }
        if (filter.getBranchId() != null) {
            query.setParameter("branchId", filter.getBranchId());
            countQuery.setParameter("branchId", filter.getBranchId());
        }
        if (filter.getRole() != null) {
            query.setParameter("role", filter.getRole());
            countQuery.setParameter("role", filter.getRole());
        } else if (!CollectionUtils.isEmpty(filter.getRoles())) {
            query.setParameter("roles", filter.getRoles());
            countQuery.setParameter("roles", filter.getRoles());
        }
        if (filter.getStatus() != null) {
            query.setParameter("status", UserStatus.valueOf(filter.getStatus()));
            countQuery.setParameter("status", UserStatus.valueOf(filter.getStatus()));
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
