package uz.uzkassa.smartposrestaurant.repository;

import uz.uzkassa.smartposrestaurant.domain.auth.Authority;

import java.util.Optional;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 24.10.2022 20: 17
 */
public interface AuthorityRepository extends BaseRepository<Authority, String> {
    Optional<Authority> findByName(String name);
}
