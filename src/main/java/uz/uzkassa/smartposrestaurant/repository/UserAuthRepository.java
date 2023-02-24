package uz.uzkassa.smartposrestaurant.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.uzkassa.smartposrestaurant.domain.UserAuth;

import java.util.Optional;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 26.10.2022 15:53
 */
public interface UserAuthRepository extends BaseRepository<UserAuth, String> {

    @Modifying
    @Query(value = "update UserAuth set deleted=true where phone=:phone and id<>:ignoreId")
    void deleteUserSecretKeys(@Param("phone") String phone, @Param("ignoreId") String ignoreId);

    @Modifying
    @Query(value = "update UserAuth set deleted=true where phone=:phone")
    void deleteUserSecretKeys(@Param("phone") String phone);

    Optional<UserAuth> findFirstByPhoneAndSecretKeyAndDeletedFalseOrderByCreatedDateDesc(String phone, String secretKey);
}
