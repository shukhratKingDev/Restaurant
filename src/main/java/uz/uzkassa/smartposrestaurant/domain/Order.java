package uz.uzkassa.smartposrestaurant.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import uz.uzkassa.smartposrestaurant.domain.auth.User;
import uz.uzkassa.smartposrestaurant.domain.base.AbstractAuditingEntity;
import uz.uzkassa.smartposrestaurant.dto.base.CommonDTO;
import uz.uzkassa.smartposrestaurant.enums.OrderStatus;
import uz.uzkassa.smartposrestaurant.enums.PaymentStatus;
import uz.uzkassa.smartposrestaurant.enums.PaymentType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 11.24.2022 18:44
 */
@Getter
@Setter
@Entity
@Table(name = "orders")
@SQLDelete(sql = "UPDATE orders set deleted='true' where id=?")
public class Order extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 235610L;

    @NotNull
    @Column(name = "order_number")
    String orderNumber;

    @Column(name = "increment_number")
    Long incrementNumber;

    @Column(name = "order_date")
    LocalDateTime orderDate;

    @Column(name = "user_id")
    String userId;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    User user;

    @Column(name = "branch_id")
    String branchId;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id", insertable = false, updatable = false)
    Branch branch;

    @Column(name = "order_status")
    @Enumerated(EnumType.STRING)
    OrderStatus status = OrderStatus.NEW;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @Where(clause = "deleted='false'")
    List<OrderItem> orderItems = new ArrayList<>();

    @Column(name = "total_price")
    BigDecimal totalPrice;

    @Column(name = "total_qty")
    BigDecimal totalQty;

    @Column(name = "payment_status")
    @Enumerated(EnumType.STRING)
    PaymentStatus paymentStatus = PaymentStatus.NOT_PAID;

    @Column(name = "payment_type")
    @Enumerated(EnumType.STRING)
    PaymentType paymentType;

    @Override
    public String getName() {
        return this.orderNumber;
    }

    public CommonDTO toCommonDTO() {
        return new CommonDTO(getId(), getName());
    }

    public void addItem(OrderItem orderItem) {
        orderItem.setOrder(this);
        orderItem.setOrderId(this.id);
        orderItems.add(orderItem);
    }

}
