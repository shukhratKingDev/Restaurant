package uz.uzkassa.smartposrestaurant.repository.impl;

import uz.uzkassa.smartposrestaurant.domain.Branch;
import uz.uzkassa.smartposrestaurant.dto.base.ResultList;
import uz.uzkassa.smartposrestaurant.enums.CommonStatus;
import uz.uzkassa.smartposrestaurant.filters.BaseFilter;
import uz.uzkassa.smartposrestaurant.repository.BranchRepositoryCustom;
import uz.uzkassa.smartposrestaurant.utils.SecurityUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Optional;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 10.10.2022 14:18
 */
public class BranchRepositoryImpl implements BranchRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public ResultList<Branch> getResultList(BaseFilter filter) {
        String companyId = Optional.ofNullable(filter.getCompanyId()).orElse(SecurityUtils.getCurrentCompanyId());
        ResultList<Branch> resultList = new ResultList<>();
        StringBuilder sql = new StringBuilder();
        sql.append(" select b from Branch b ");
        sql.append(" join b.company c ");
        sql.append(" where b.deleted is not true ");
        sql.append(" and c.id =:companyId ");

        if (filter.getStatus() != null) {
            sql.append(" and b.status =:status");
        }
        if (filter.getRegionId() != null) {
            sql.append(" and b.address.regionId =:regionId ");
        }
        if (filter.getDistrictId() != null) {
            sql.append(" and b.address.districtId =:districtId ");
        }
        if (filter.getFrom() != null) {
            sql.append(" and b.createdDate >=:fromDate");
        }
        if (filter.getTo() != null) {
            sql.append(" and b.createdDate <=:toDate ");
        }
        if (filter.isNotEmpty()) {
            sql.append(" and ( ");
            sql.append(" lower(b.name)").append(filter.getLikeSearchKey());
            sql.append(" or lower(c.name)").append(filter.getLikeSearchKey());
            sql.append(" or lower(c.tin)").append(filter.getLikeSearchKey());
            sql.append(") ");
        }

        String countSql = sql.toString().replaceFirst("select b", "select count(b.id)");

        sql.append(" order by b.").append(filter.getOrderBy());
        sql.append(" ").append(filter.getSortOrder().getName());

        TypedQuery<Branch> query = entityManager
            .createQuery(sql.toString(), Branch.class)
            .setFirstResult(filter.getStart())
            .setMaxResults(filter.getSize());

        TypedQuery<Long> countQuery = entityManager.createQuery(countSql, Long.class);

        query.setParameter("companyId", companyId);
        countQuery.setParameter("companyId", companyId);

        if (filter.getRegionId() != null) {
            query.setParameter("regionId", filter.getRegionId());
            countQuery.setParameter("regionId", filter.getRegionId());
        }
        if (filter.getDistrictId() != null) {
            query.setParameter("districtId", filter.getDistrictId());
            countQuery.setParameter("districtId", filter.getDistrictId());
        }
        if (filter.getStatus() != null) {
            query.setParameter("status", CommonStatus.valueOf(filter.getStatus()));
            countQuery.setParameter("status", CommonStatus.valueOf(filter.getStatus()));
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
