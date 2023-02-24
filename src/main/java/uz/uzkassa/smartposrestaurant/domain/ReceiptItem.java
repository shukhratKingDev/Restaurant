package uz.uzkassa.smartposrestaurant.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import uz.uzkassa.smartposrestaurant.domain.base.BaseEntity;

import javax.persistence.*;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 14.10.2022 18:49
 */
@Getter
@Setter
@Entity
@Table(name = "receipt_item")
@SQLDelete(sql = "UPDATE receipt_item set deleted='true' WHERE id=?")
public class ReceiptItem extends BaseEntity {

    @Column(name = "menu_item_id")
    String menuItemId;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_item_id", insertable = false, updatable = false)
    MenuItem item;

    @Column(name = "receipt_id")
    String receiptId;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "receipt_id", insertable = false, updatable = false)
    Receipt receipt;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ReceiptItem)) {
            return false;
        }
        return id != null && id.equals(((ReceiptItem) o).id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
