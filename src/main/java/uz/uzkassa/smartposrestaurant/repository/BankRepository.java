package uz.uzkassa.smartposrestaurant.repository;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.EntityGraph;
import uz.uzkassa.smartposrestaurant.constants.CacheConstants;
import uz.uzkassa.smartposrestaurant.domain.Bank;

import java.util.Optional;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date 10.10.2022 13:55
 */
public interface BankRepository extends BaseRepository<Bank, String>, BankRepositoryCustom {
    @Cacheable(cacheNames = CacheConstants.BANK_BY_MFO_CACHE)
    Optional<Bank> findByMfo(String mfo);

    @Cacheable(cacheNames = CacheConstants.BANK_BY_MFO_CACHE)
    @EntityGraph(attributePaths = {"parent"})
    Optional<Bank> findFirstByMfo(String mfo);
}
