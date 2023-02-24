package uz.uzkassa.smartposrestaurant.repository.impl;

import uz.uzkassa.smartposrestaurant.domain.company.Company;
import uz.uzkassa.smartposrestaurant.dto.base.ResultList;
import uz.uzkassa.smartposrestaurant.filters.CompanyFilter;
import uz.uzkassa.smartposrestaurant.repository.CompanyRepositoryCustom;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 10.10.2022 14:19
 */
public class CompanyRepositoryImpl implements CompanyRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public ResultList<Company> getResultList(CompanyFilter filter) {
        ResultList<Company> resultList = new ResultList<>();
        StringBuilder sql = new StringBuilder();
        sql.append(" select c from Company c ");
        sql.append(" join c.owner o");
        sql.append(" where c.deleted is not true and o.deleted is not true ");
        sql.append(" and c.tin is not null ");

        if (filter.getActivityTypeId() != null) {
            sql.append(" and c.activityTypeId=:activityTypeId ");
        }
        if (filter.getBusinessType() != null) {
            sql.append(" and c.businessType=:businessType ");
        }
        if (filter.getRegionId() != null) {
            sql.append(" and c.address.regionId=:regionId ");
        }
        if (filter.getDistrictId() != null) {
            sql.append(" and c.address.districtId=:districtId ");
        }
        if (filter.getFrom() != null) {
            sql.append(" and c.createdDate>=:fromDate ");
        }
        if (filter.getTo() != null) {
            sql.append(" and c.createdDate<=:toDate ");
        }
        if (filter.isNotEmpty()) {
            sql.append(" and (");
            sql.append(" lower(c.name) ").append(filter.getLikeSearchKey());
            sql.append(" or lower(c.tin) ").append(filter.getLikeSearchKey());
            sql.append(" or lower(o.phone) ").append(filter.getLikeSearchKey());
            sql.append(" )");
        }

        String countSql = sql.toString().replaceFirst("select c", "select count(c.id)");

        sql.append(" order by c.").append(filter.getOrderBy());
        sql.append(" ").append(filter.getSortOrder().getName());

        TypedQuery<Company> query = entityManager
            .createQuery(sql.toString(), Company.class)
            .setFirstResult(filter.getStart())
            .setMaxResults(filter.getSize());

        TypedQuery<Long> countQuery = entityManager.createQuery(countSql, Long.class);

        if (filter.getActivityTypeId() != null) {
            query.setParameter("activityTypeId", filter.getActivityTypeId());
            countQuery.setParameter("activityTypeId", filter.getActivityTypeId());
        }
        if (filter.getBusinessType() != null) {
            query.setParameter("businessType", filter.getBusinessType());
            countQuery.setParameter("businessType", filter.getBusinessType());
        }
        if (filter.getRegionId() != null) {
            query.setParameter("regionId", filter.getRegionId());
            countQuery.setParameter("regionId", filter.getRegionId());
        }
        if (filter.getDistrictId() != null) {
            query.setParameter("districtId", filter.getDistrictId());
            countQuery.setParameter("districtId", filter.getDistrictId());
        }
        if (filter.getDistrictId() != null) {
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

        Long listCount = countQuery.getSingleResult();
        if (listCount > 0) {
            resultList.setList(query.getResultList());
            resultList.setCount(listCount);
        }
        return resultList;
    }
}
