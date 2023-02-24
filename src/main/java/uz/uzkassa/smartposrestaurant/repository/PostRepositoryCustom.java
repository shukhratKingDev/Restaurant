package uz.uzkassa.smartposrestaurant.repository;

import uz.uzkassa.smartposrestaurant.domain.post.Post;
import uz.uzkassa.smartposrestaurant.dto.base.ResultList;
import uz.uzkassa.smartposrestaurant.filters.BaseFilter;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 17.10.2022 17:42
 */
public interface PostRepositoryCustom {
    ResultList<Post> getResultList(BaseFilter filter);
}
