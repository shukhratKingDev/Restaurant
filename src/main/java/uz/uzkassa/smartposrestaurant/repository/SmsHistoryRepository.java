package uz.uzkassa.smartposrestaurant.repository;

import uz.uzkassa.smartposrestaurant.domain.SmsHistory;

import java.util.Optional;

/**
 * Powered by : Shuxratjon Rayimjonov
 * Date: 26.10.2022 15:36
 */
public interface SmsHistoryRepository extends BaseRepository<SmsHistory, String> {

    Optional<SmsHistory> findFirstByPhoneAndCodeOrderByCreatedDateDesc(String phone, String code);

    void deleteAllByPhone(String phone);
}
