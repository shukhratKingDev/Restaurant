package uz.uzkassa.smartposrestaurant.domain;

import lombok.Getter;
import lombok.Setter;
import uz.uzkassa.smartposrestaurant.domain.base.AbstractAuditingEntity;
import uz.uzkassa.smartposrestaurant.enums.ProductType;
import uz.uzkassa.smartposrestaurant.enums.Vat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 14.10.2022 18:55
 */
@Getter
@Setter
@Table(name = "product")
@Entity
public class Product extends AbstractAuditingEntity implements Serializable {

    static final long serialVersionUID = 67L;

    @Column(name = "tasnif_id")
    String tasnifId;

    @NotNull
    @Column(name = "name")
    String name;

    @Column(name = "brand")
    String brand;

    @Column(name = "barcode")
    String barcode;

    @Column(name = "vat_barcode")
    String vatBarcode;

    @Column(name = "catalog_name")
    String catalogName;

    @Column(name = "package_name")
    String packageName;

    @Column(name = "package_code")
    String packageCode;

    @Column(name = "qty_in_package")
    BigDecimal qtyInPackage;

    @Column(name = "capacity")
    String capacity;

    @Column(name = "product_type")
    @Enumerated(value = EnumType.STRING)
    ProductType productType;

    @NotNull
    @Column(name = "unit_id", nullable = false)
    String unitId;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "unit_id", insertable = false, updatable = false)
    Unit unit;

    @Column(name = "vat")
    @Enumerated(EnumType.STRING)
    Vat vat = Vat.WITHOUT_VAT;

    @Column(name = "price", scale = 2, precision = 25)
    BigDecimal price;

    @NotNull
    @Column(name = "category_id", nullable = false)
    String categoryId;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", insertable = false, updatable = false)
    Category category;

    @NotNull
    @Column(name = "branch_id")
    String branchId;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id", insertable = false, updatable = false)
    Branch branch;

    @Column(name = "has_mark", columnDefinition = "boolean default false")
    boolean hasMark;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Product)) {
            return false;
        }
        return id != null && id.equals(((Product) o).id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    public boolean isSynced() {
        return getVatBarcode() != null && getCatalogName() != null && getPackageName() != null && getPackageCode() != null;
    }
}
