package uz.uzkassa.smartposrestaurant.service;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 10.10.2022  14:46
 */
@Service
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class CompanyBankService extends BaseService {
}
