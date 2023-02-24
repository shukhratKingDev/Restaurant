package uz.uzkassa.smartposrestaurant.web.rest.cabinet;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.uzkassa.smartposrestaurant.constants.ApiConstants;
import uz.uzkassa.smartposrestaurant.dto.OrderDTO;
import uz.uzkassa.smartposrestaurant.dto.base.CommonDTO;
import uz.uzkassa.smartposrestaurant.dto.order.OrderDetailDTO;
import uz.uzkassa.smartposrestaurant.dto.order.OrderListDTO;
import uz.uzkassa.smartposrestaurant.enums.OrderStatus;
import uz.uzkassa.smartposrestaurant.enums.PaymentStatus;
import uz.uzkassa.smartposrestaurant.enums.PaymentType;
import uz.uzkassa.smartposrestaurant.filters.OrderFilter;
import uz.uzkassa.smartposrestaurant.service.OrderService;
import uz.uzkassa.smartposrestaurant.utils.SecurityUtils;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 11.24.2022 19:56
 */
@RestController
@RequestMapping(ApiConstants.cabinetOrderRootApi)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CabinetOrderResource {

    OrderService orderService;

    @PostMapping
    public ResponseEntity<String> create(@RequestBody OrderDTO orderDTO) {
        return ResponseEntity.ok(orderService.create(orderDTO));
    }

    @GetMapping(ApiConstants.id)
    public ResponseEntity<OrderDetailDTO> get(@PathVariable String id) {
        return ResponseEntity.ok(orderService.get(id));
    }

    @PutMapping(ApiConstants.id)
    public ResponseEntity<String> update(@PathVariable String id, @RequestBody OrderDTO orderDTO) {
        return ResponseEntity.ok(orderService.update(id, orderDTO));

    }

    @DeleteMapping(ApiConstants.id)
    public ResponseEntity<Void> delete(@PathVariable String id) {
        orderService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<Page<OrderListDTO>> getList(OrderFilter filter) {
        if (SecurityUtils.getCurrentCompanyId() == null) {
            return ResponseEntity.ok(new PageImpl<>(new ArrayList<>(), filter.getPageable(), 0L));
        }
        return ResponseEntity.ok(orderService.getList(filter));
    }

    @GetMapping(ApiConstants.lookUp)
    public ResponseEntity<List<CommonDTO>> lookUp(OrderFilter filter) {
        if (SecurityUtils.getCurrentBranchId() == null) {
            return ResponseEntity.ok(new ArrayList<>());
        }
        return ResponseEntity.ok(orderService.lookUp(filter));
    }

    @PutMapping(ApiConstants.updateNumber)
    public ResponseEntity<Void> updateNumber(@PathVariable String id, @RequestParam String orderNumber) {
        orderService.updateNumber(id, orderNumber.trim());
        return ResponseEntity.ok().build();
    }

    @GetMapping(ApiConstants.statuses)
    public ResponseEntity<EnumSet<OrderStatus>> getOrderStatuses() {
        return ResponseEntity.ok().body(EnumSet.allOf(OrderStatus.class));
    }

    @GetMapping("/payment-statuses")
    public ResponseEntity<EnumSet<PaymentStatus>> getPaymentStatuses() {
        return ResponseEntity.ok().body(EnumSet.allOf(PaymentStatus.class));
    }

    @GetMapping("/payment-types")
    public ResponseEntity<EnumSet<PaymentType>> getPaymentTypes() {
        return ResponseEntity.ok().body(EnumSet.allOf(PaymentType.class));
    }

    @PutMapping(ApiConstants.updateStatus)
    public ResponseEntity<Void> updateStatus(@PathVariable String id, @RequestParam String status) {
        orderService.updateStatus(id, OrderStatus.valueOf(status));
        return ResponseEntity.ok().build();
    }

}
