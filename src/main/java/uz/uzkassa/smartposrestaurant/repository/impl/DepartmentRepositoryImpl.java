package uz.uzkassa.smartposrestaurant.repository.impl;

import uz.uzkassa.smartposrestaurant.domain.Department;
import uz.uzkassa.smartposrestaurant.dto.base.ResultList;
import uz.uzkassa.smartposrestaurant.filters.BaseFilter;
import uz.uzkassa.smartposrestaurant.repository.DepartmentRepositoryCustom;
import uz.uzkassa.smartposrestaurant.utils.SecurityUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Optional;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 10.10.2022 14:22
 */
public class DepartmentRepositoryImpl implements DepartmentRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public ResultList<Department> getResultList(BaseFilter filter) {
        String branchId = Optional.ofNullable(filter.getBranchId()).orElse(SecurityUtils.getCurrentBranchId());
        ResultList<Department> resultList = new ResultList<>();
        StringBuilder sql = new StringBuilder();
        sql.append(" select d from Department d");
        sql.append(" join d.branch b ");
        sql.append(" where d.deleted is not true ");
        sql.append(" and b.id=:branchId");

        if (filter.isNotEmpty()) {
            sql.append(" and ( ");
            sql.append(" lower(d.name)").append(filter.getLikeSearchKey());
            sql.append(" or lower(b.name)").append(filter.getLikeSearchKey());
            sql.append(" )");
        }

        String countSql = sql.toString().replaceFirst("select d", "select count(d.id)");

        sql.append(" order by d.").append(filter.getOrderBy());
        sql.append(" ").append(filter.getSortOrder().getName());

        TypedQuery<Department> query = entityManager
            .createQuery(sql.toString(), Department.class)
            .setFirstResult(filter.getStart())
            .setMaxResults(filter.getSize());

        TypedQuery<Long> countQuery = entityManager.createQuery(countSql, Long.class).setMaxResults(1);

        query.setParameter("branchId", branchId);
        countQuery.setParameter("branchId", branchId);

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
