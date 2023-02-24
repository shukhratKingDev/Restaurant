package uz.uzkassa.smartposrestaurant.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.uzkassa.smartposrestaurant.domain.Order;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 11.25.2022 10:15
 */

public interface OrderRepository extends BaseRepository<Order, String>, OrderRepositoryCustom {

    @Query("select coalesce(max (o.incrementNumber),0)from Order o where o.companyId=:companyId")
    Long getMaxIncrementNumber(@Param("companyId") String companyId);

    boolean existsByCompanyIdAndOrderNumberAndIdNot(String companyId, String orderNumber, String id);
}
