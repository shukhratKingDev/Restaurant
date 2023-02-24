package uz.uzkassa.smartposrestaurant.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import uz.uzkassa.smartposrestaurant.domain.base.BaseEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 11.24.2022 19:12
 */
@Getter
@Setter
@Table(name = "order_item")
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderItem extends BaseEntity implements Serializable {

    @Column(name = "menu_item_id")
    String menuItemId;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_item_id", insertable = false, updatable = false)
    MenuItem menuItem;

    @Column(name = "qty")
    BigDecimal qty;

    @Column(name = "price")
    BigDecimal price;

    @Column(name = "unit_id")
    String unitId;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "unit_id", insertable = false, updatable = false)
    Unit unit;

    @Column(name = "total_price")
    BigDecimal totalPrice;

    @NotNull
    @Column(name = "order_id", nullable = false)
    String orderId;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", updatable = false, insertable = false)
    Order order;

}
