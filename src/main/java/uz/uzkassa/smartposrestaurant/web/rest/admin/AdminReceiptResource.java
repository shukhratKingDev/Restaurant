package uz.uzkassa.smartposrestaurant.web.rest.admin;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.uzkassa.smartposrestaurant.constants.ApiConstants;
import uz.uzkassa.smartposrestaurant.dto.receipt.ReceiptDTO;
import uz.uzkassa.smartposrestaurant.service.ReceiptService;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 18.10.2022 17:41
 */
@RestController
@RequestMapping(ApiConstants.adminReceiptRooApi)
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class AdminReceiptResource {

    ReceiptService receiptService;

    @PostMapping
    public ResponseEntity<String> create(ReceiptDTO receiptDTO) {
        return ResponseEntity.ok(receiptService.create(receiptDTO));
    }
}
