package uz.uzkassa.smartposrestaurant.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import uz.uzkassa.smartposrestaurant.domain.base.BaseEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 17.10.2022 15:18
 */
@Getter
@Setter
@Table(name = "menu_item")
@Entity
@SQLDelete(sql = "UPDATE menu_item SET deleted='true' WHERE id=?")
public class MenuItem extends BaseEntity implements Serializable {

    @Column(name = "name")
    String name;

    @Column(name = "menu_id")
    String menuId;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id", insertable = false, updatable = false)
    Menu menu;

    @Column(name = "product_id")
    String productId;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", insertable = false, updatable = false)
    Product product;

    @Column(name = "unit_id")
    String unitId;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "unit_id", insertable = false, updatable = false)
    Unit unit;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MenuItem)) {
            return false;
        }
        return id != null && id.equals(((MenuItem) o).id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
