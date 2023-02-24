package uz.uzkassa.smartposrestaurant.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.uzkassa.smartposrestaurant.dto.receipt.ReceiptDTO;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 18.10.2022 17: 48
 */
@Service
@Transactional
public class ReceiptService extends BaseService {

    public String create(ReceiptDTO receiptDTO) {
        return null;
    }
}
