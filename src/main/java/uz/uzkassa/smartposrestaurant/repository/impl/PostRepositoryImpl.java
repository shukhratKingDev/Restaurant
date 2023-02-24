package uz.uzkassa.smartposrestaurant.repository.impl;

import uz.uzkassa.smartposrestaurant.domain.post.Post;
import uz.uzkassa.smartposrestaurant.dto.base.ResultList;
import uz.uzkassa.smartposrestaurant.filters.BaseFilter;
import uz.uzkassa.smartposrestaurant.repository.PostRepositoryCustom;
import uz.uzkassa.smartposrestaurant.utils.SecurityUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Optional;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 17.10.2022 17.44
 */
public class PostRepositoryImpl implements PostRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public ResultList<Post> getResultList(BaseFilter filter) {
        String branchId = Optional.ofNullable(filter.getBranchId()).orElse(SecurityUtils.getCurrentBranchId());
        ResultList<Post> resultList = new ResultList<>();
        StringBuilder sql = new StringBuilder();
        sql.append(" select p from Post p");
        sql.append(" where p.deleted is not true");
        if (filter.getBranchId() != null) {
            sql.append(" and p.branchId=:branchId ");
        }
        if (filter.isNotEmpty()) {
            sql.append(" and (");
            sql.append(" lower(p.name)").append(filter.getLikeSearchKey());
            sql.append(" )");
        }

        String countSql = sql.toString().replaceFirst("select p", "select count(p.id)");
        sql.append(" order by p.").append(filter.getOrderBy());
        sql.append(" ").append(filter.getSortOrder().getName());

        TypedQuery<Post> query = entityManager
            .createQuery(sql.toString(), Post.class)
            .setFirstResult(filter.getStart())
            .setMaxResults(filter.getSize());

        TypedQuery<Long> countQuery = entityManager.createQuery(countSql, Long.class);

        if (branchId != null) {
            query.setParameter("branchId", branchId);
            countQuery.setParameter("branchId", branchId);
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
