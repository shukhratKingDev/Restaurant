package uz.uzkassa.smartposrestaurant.repository;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.uzkassa.smartposrestaurant.constants.CacheConstants;
import uz.uzkassa.smartposrestaurant.domain.auth.User;

import java.util.List;
import java.util.Optional;

/**
 * Powered by Shuxratjon Rayimjonov
 * Date: 07.10.2022 19:59
 */

public interface UserRepository extends BaseRepository<User, String>, UserRepositoryCustom {
    @EntityGraph(attributePaths = {"authorities","company","branch"})
    @Cacheable(CacheConstants.USERS_BY_LOGIN_CACHE)
    Optional<User> findOneWithAuthoritiesByLoginAndDeletedFalse(String login);

    Optional<User> findOneByLoginAndDeletedIsFalse(String login);

    Optional<User> findByTinAndDeletedIsFalse(String tin);

    Optional<User> findByPinflAndDeletedIsFalse(String pinfl);

    boolean existsUserByLoginAndIdNot(String login, String id);

    boolean existsUserByTinAndIdNot(String tin, String id);

    boolean existsUserByPinflAndIdNot(String pinfl, String id);

    @Query("SELECT  u.id from User u JOIN u.authorities a WHERE  u.deleted=false and a.name=:role ")
    List<String> getIdsByRole(@Param("role") String role);

    Optional<User> findFirstByLoginAndDeletedIsFalse(String login);
}
