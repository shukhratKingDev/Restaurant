package uz.uzkassa.smartposrestaurant.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import uz.uzkassa.smartposrestaurant.domain.auth.User;
import uz.uzkassa.smartposrestaurant.domain.base.AbstractAuditingEntity;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 11.28.2022 19:26
 */
@Getter
@Setter
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "reservation")
public class Reservation extends AbstractAuditingEntity {

    private static final long serialVersionUID = 83948398L;

    @Column(name = "branch_id", nullable = false)
    String branchId;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id", insertable = false, updatable = false)
    Branch branch;

    @Column(name = "number_of_guest")
    Integer numberOfGuest;

    @Column(name = "reservation_date")
    LocalDateTime reservationDate;

    @Column(name = "client_id")
    String clientId;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", insertable = false, updatable = false)
    User client;

    @Column(name = "order_id")
    String orderId;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", insertable = false, updatable = false)
    Order order;
}
