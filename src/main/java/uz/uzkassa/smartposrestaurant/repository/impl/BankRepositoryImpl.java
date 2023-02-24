package uz.uzkassa.smartposrestaurant.repository.impl;

import uz.uzkassa.smartposrestaurant.domain.Bank;
import uz.uzkassa.smartposrestaurant.dto.base.ResultList;
import uz.uzkassa.smartposrestaurant.filters.BaseFilter;
import uz.uzkassa.smartposrestaurant.repository.BankRepositoryCustom;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 10.10.2022 14:16
 */
public class BankRepositoryImpl implements BankRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public ResultList<Bank> getResultList(BaseFilter filter) {
        ResultList<Bank> resultList = new ResultList<>();
        StringBuilder sql = new StringBuilder();
        sql.append("select b from Bank b \n");
        sql.append(" where b.deleted is not true ");
        if (filter.getParentId() != null) {
            sql.append(" and b.parentId=:parentId \n");
        } else {
            sql.append(" and b.parentId is null \n");
        }
        if (filter.isNotEmpty()) {

            sql.append(" and (");
            sql.append(" lower(b.nameUz) ").append(filter.getLikeSearchKey());
            sql.append(" or lower(b.nameRu) ").append(filter.getLikeSearchKey());
            sql.append(" or lower(b.nameCyrillic) ").append(filter.getLikeSearchKey());
            sql.append(" or lower(b.mfo)").append(filter.getLikeSearchKey());
            sql.append(" )");
        }

        String countSql = sql.toString().replaceFirst("select b", " select count(b.id)");
        sql.append(" order by b.nameRu");

        TypedQuery<Bank> query = entityManager
            .createQuery(sql.toString(), Bank.class)
            .setFirstResult(filter.getSize())
            .setMaxResults(filter.getSize());

        TypedQuery<Long> countQuery = entityManager.createQuery(countSql, Long.class).setMaxResults(1);

        if (filter.isNotEmpty()) {
            query.setParameter("searchKey", filter.getSearchForQuery());
            countQuery.setParameter("searchKey", filter.getSearchForQuery());
        }
        if (filter.getParentId() != null) {
            query.setParameter("parentId", filter.getParentId());
            countQuery.setParameter("parentId", filter.getParentId());
        }
        Long count = countQuery.getSingleResult();
        if (count > 0) {
            resultList.setList(query.getResultList());
            resultList.setCount(count);
        }
        return resultList;
    }
}
