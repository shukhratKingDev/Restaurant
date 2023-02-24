package uz.uzkassa.smartposrestaurant.web.rest.admin;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.uzkassa.smartposrestaurant.constants.ApiConstants;
import uz.uzkassa.smartposrestaurant.dto.product.ProductDTO;
import uz.uzkassa.smartposrestaurant.service.ProductService;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 18.10.2022 15:51
 */
@RestController
@RequestMapping(ApiConstants.adminProductRootApi)
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class AdminProductResource {

    ProductService productService;

    @PostMapping
    public ResponseEntity<String> create(@RequestBody ProductDTO productDTO) {
        return ResponseEntity.ok(productService.create(productDTO));
    }
}
