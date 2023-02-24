package uz.uzkassa.smartposrestaurant.service;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.uzkassa.smartposrestaurant.domain.Order;
import uz.uzkassa.smartposrestaurant.domain.OrderItem;
import uz.uzkassa.smartposrestaurant.dto.OrderDTO;
import uz.uzkassa.smartposrestaurant.dto.OrderItemDTO;
import uz.uzkassa.smartposrestaurant.dto.base.CommonDTO;
import uz.uzkassa.smartposrestaurant.dto.base.ResultList;
import uz.uzkassa.smartposrestaurant.dto.order.OrderDetailDTO;
import uz.uzkassa.smartposrestaurant.dto.order.OrderItemDetailDTO;
import uz.uzkassa.smartposrestaurant.dto.order.OrderListDTO;
import uz.uzkassa.smartposrestaurant.enums.OrderStatus;
import uz.uzkassa.smartposrestaurant.enums.Prefix;
import uz.uzkassa.smartposrestaurant.filters.OrderFilter;
import uz.uzkassa.smartposrestaurant.repository.OrderItemRepository;
import uz.uzkassa.smartposrestaurant.repository.OrderRepository;
import uz.uzkassa.smartposrestaurant.utils.DateUtils;
import uz.uzkassa.smartposrestaurant.web.rest.errors.BadRequestException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 11.24.2022 20:08
 */
@Service
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Transactional
public class OrderService extends BaseService {
    OrderRepository orderRepository;
    OrderItemRepository orderItemRepository;

    public String create(OrderDTO orderDTO) {
        Long incrementNumber = orderRepository.getMaxIncrementNumber(getCurrentCompanyId()) + 1L;
        String orderNumber = Prefix.ORDER
            .getName()
            .concat(DateUtils.formatYearLastTwoDigit(LocalDate.now()))
            .concat("-")
            .concat(commonNumberingFormat.format(incrementNumber));

        Order order = new Order();
        order.setOrderNumber(orderNumber);
        order.setIncrementNumber(incrementNumber);
        order.setUserId(orderDTO.getUserId());
        order.setCompanyId(getCurrentCompanyId());
        order.setBranchId(orderDTO.getBranchId());
        order.setOrderDate(LocalDateTime.now());
        order.setTotalPrice(orderDTO.getTotalPrice());
        order.setTotalQty(orderDTO.getTotalQty());
        order.setPaymentStatus(orderDTO.getPaymentStatus());
        order.setPaymentType(orderDTO.getPaymentType());
        orderDTO.getOrderItems().forEach(orderItemDTO -> {
            validateOrder(orderItemDTO);
            OrderItem orderItem = new OrderItem();
            orderItem.setMenuItemId(orderItemDTO.getId());
            orderItem.setUnitId(orderItem.getUnitId());
            orderItem.setQty(orderItemDTO.getQty());
            orderItem.setPrice(orderItemDTO.getPrice());
            orderItem.setTotalPrice(orderItemDTO.getTotalPrice());
            order.addItem(orderItem);
        });
        orderRepository.save(order);
        orderDTO.setId(order.getId());
        return order.getId();
    }

    @Transactional(readOnly = true)
    public OrderDetailDTO get(String id) {
        Order order = orderRepository.findById(id).orElseThrow(notFoundExceptionThrow(Order.class.getSimpleName(), "id", id));
        OrderDetailDTO orderDetailDTO = new OrderDetailDTO();
        orderDetailDTO.setId(order.getId());
        orderDetailDTO.setOrderNumber(order.getOrderNumber());
        orderDetailDTO.setOrderDate(order.getOrderDate());
        if (order.getUser() != null) {
            orderDetailDTO.setUser(order.getUser().toCommonDTO());
        }
        if (order.getBranch() != null) {
            orderDetailDTO.setBranch(order.getBranch().toCommonDTO());
        }
        orderDetailDTO.setStatus(order.getStatus());
        orderDetailDTO.setTotalPrice(order.getTotalPrice());
        orderDetailDTO.setTotalQty(order.getTotalQty());
        order.setPaymentStatus(order.getPaymentStatus());

        order.getOrderItems().forEach(orderItem -> {
            OrderItemDetailDTO orderItemDetailDTO = new OrderItemDetailDTO();
            orderItemDetailDTO.setOrderItemId(orderItem.getId());
            orderItemDetailDTO.setId(orderItem.getMenuItemId());
            orderItemDetailDTO.setName(orderItem.getMenuItem().getName());
            orderItemDetailDTO.setUnit(orderItem.getMenuItem().getUnit().toDTO());
            orderItemDetailDTO.setQty(orderItem.getQty());
            orderItemDetailDTO.setPrice(orderItem.getPrice());
            orderItemDetailDTO.setTotalPrice(orderItem.getTotalPrice());
            orderDetailDTO.addItem(orderItemDetailDTO);
        });
        return orderDetailDTO;
    }

    public String update(String id, OrderDTO orderDTO) {
        Order order = orderRepository.findById(id).orElseThrow(notFoundExceptionThrow(Order.class.getSimpleName(), "id", id));
        if (OrderStatus.READY.equals(order.getStatus())) {
            throw new BadRequestException(localizationService.localize("you.cannot.update.order"));
        }
        order.setUserId(orderDTO.getUserId());
        order.setBranchId(order.getBranchId());
        order.setStatus(order.getStatus());
        order.setTotalPrice(order.getTotalPrice());
        order.setTotalQty(order.getTotalQty());
        order.setPaymentStatus(order.getPaymentStatus());

        HashMap<String, OrderItem> orderItemsMap = new HashMap<>();
        order.getOrderItems().forEach(orderItem -> orderItemsMap.put(orderItem.getId(), orderItem));
        orderDTO.getOrderItems().forEach(orderItemDTO -> {
            OrderItem orderItem;
            if (orderItemDTO.getOrderItemId() != null && orderItemsMap.get(orderItemDTO.getOrderItemId()) != null) {
                orderItem = orderItemsMap.get(orderItemDTO.getOrderItemId());
            } else {
                orderItem = new OrderItem();
            }
            validateOrder(orderItemDTO);
            orderItem.setOrderId(orderItemDTO.getId());
            orderItem.setUnitId(orderItemDTO.getUnitId());
            orderItem.setQty(orderItemDTO.getQty());
            orderItem.setPrice(orderItemDTO.getPrice());
            orderItem.setTotalPrice(orderItemDTO.getTotalPrice());

            order.addItem(orderItem);
            if (orderItem.getId() != null) {
                orderItemsMap.remove(orderItem.getId());
            }
        });
        orderRepository.save(order);
        if (!orderItemsMap.isEmpty()) {
            orderItemRepository.deleteAllByIdInBatch(orderItemsMap.keySet());
        }
        orderDTO.setId(id);
        return order.getId();
    }

    public void delete(String id) {
        orderRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Page<OrderListDTO> getList(OrderFilter filter) {
        List<OrderListDTO> result = new ArrayList<>();
        ResultList<Order> resultList = orderRepository.getResultList(filter);
        resultList.getList()
            .forEach(order -> {
                OrderListDTO orderListDTO = new OrderListDTO();
                orderListDTO.setId(order.getId());
                if (order.getUserId() != null) {
                    orderListDTO.setUser(order.getUser().toCommonDTO());
                }
                orderListDTO.setOrderDate(order.getOrderDate());
                orderListDTO.setStatus(order.getStatus());
                orderListDTO.setOrderNumber(order.getOrderNumber());
                if (order.getBranch() != null) {
                    orderListDTO.setBranch(order.getBranch().toCommonDTO());
                }
                orderListDTO.setTotalPrice(order.getTotalPrice());
                orderListDTO.setTotalQty(order.getTotalQty());
                result.add(orderListDTO);
            });
        return new PageImpl<>(result, filter.getSortedPageable(), resultList.getCount());
    }

    @Transactional(readOnly = true)
    public List<CommonDTO> lookUp(OrderFilter filter) {
        return orderRepository.getResultList(filter).getList().stream()
            .map(Order::toCommonDTO).collect(Collectors.toList());
    }

    private void validateOrder(OrderItemDTO orderItemDTO) {
        if (orderItemDTO.getId() == null) {
            throw new BadRequestException("You must select some product");
        }
        if (orderItemDTO.getQty() == null) {
            throw new BadRequestException("Product amount must not equal to null");
        }
        if (orderItemDTO.getPrice() == null) {
            throw new BadRequestException("Product price must not equal to null");
        }
        if (orderItemDTO.getTotalPrice() == null) {
            throw new BadRequestException("Total price must not equal to null");
        }
    }

    public void updateNumber(String id, String orderNumber) {
        if (orderRepository.existsByCompanyIdAndOrderNumberAndIdNot(getCurrentCompanyId(), orderNumber, id)) {
            throw new BadRequestException("Order with this number " + orderNumber + " is already exist in the system");
        }
        orderRepository.findById(id)
            .map(order -> {
                order.setOrderNumber(orderNumber);
                orderRepository.save(order);
                return order;
            })
            .orElseThrow(notFoundExceptionThrow(Order.class.getSimpleName(), "id", id));
    }

    public void updateStatus(String id, OrderStatus status) {
        orderRepository.findById(id)
            .map(order -> {
                order.setStatus(status);
                return orderRepository.save(order);
            })
            .orElseThrow(notFoundExceptionThrow(Order.class.getSimpleName(), "id", id));
    }
}
