package uz.uzkassa.smartposrestaurant.repository.impl;

import uz.uzkassa.smartposrestaurant.domain.Category;
import uz.uzkassa.smartposrestaurant.dto.base.ResultList;
import uz.uzkassa.smartposrestaurant.dto.category.CategoryTreeDTO;
import uz.uzkassa.smartposrestaurant.enums.CommonStatus;
import uz.uzkassa.smartposrestaurant.filters.BaseFilter;
import uz.uzkassa.smartposrestaurant.repository.CategoryRepositoryCustom;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

public class CategoryRepositoryImpl implements CategoryRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public ResultList<Category> getResultList(BaseFilter filter) {
        ResultList<Category> resultList = new ResultList<>();
        StringBuilder sql = new StringBuilder();
        sql.append("select c from Category c");
        sql.append(" where c.deleted is not true and c.branchId =:branchId ");
        if (filter.getParentId() != null) {
            sql.append(" and c.parentId=:parentId ");
        }
        if (filter.isNotEmpty()) {
            sql.append(" and (");
            sql.append(" lower(c.name)").append(filter.getLikeSearchKey());
            sql.append(")");
        }

        String countSql = sql.toString().replaceFirst("select c", "select count(c.id)");
        sql.append(" order by c.").append(filter.getOrderBy());
        sql.append(" ").append(filter.getSortOrder().getName());

        TypedQuery<Category> query = entityManager.createQuery(sql.toString(), Category.class)
            .setFirstResult(filter.getStart())
            .setMaxResults(filter.getSize());

        TypedQuery<Long> countQuery = entityManager.createQuery(countSql, Long.class);

        query.setParameter("branchId", filter.getBranchId());
        countQuery.setParameter("branchId", filter.getBranchId());

        if (filter.getParentId() != null) {
            query.setParameter("parentId", filter.getParentId());
            countQuery.setParameter("parentId", filter.getParentId());
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

    @Override
    public List<CategoryTreeDTO> treeList(BaseFilter filter) {
        StringBuilder sql = new StringBuilder();
        sql.append("WITH products as (select count(p.id) as count, p.category_id as categoryId from product p ");
        sql.append(" where p.deleted is not true ");
        if (filter.getBranchId() != null) {
            sql.append(" and p.branch_id=:branchId");
        }
        sql.append(" group by p.category_id)");

        sql.append(" select distinct c.id as id, c.name as name, c.parent_id as parentId, coalesce(p.count,0) as productCount, c.status as status ");
        sql.append(" from category c ");
        sql.append(" left join products p ON p.categoryId = c.id ");
        sql.append(" where c.deleted is not true ");
        if (filter.getBranchId() != null) {
            sql.append(" and c.branch_id =:branchId");
        }
        if (filter.getStatus() != null) {
            sql.append(" and c.status =:status");
        }
        sql.append(" order by c.name \n");
        Query query = entityManager.createNativeQuery(sql.toString(), "CategoryTreeDTOMapper");
        if (filter.getBranchId() != null) {
            query.setParameter("branchId", filter.getBranchId());
        }
        if (filter.getStatus() != null) {
            query.setParameter("status", CommonStatus.valueOf(filter.getStatus()));
        }
        return query.getResultList();
    }
}
