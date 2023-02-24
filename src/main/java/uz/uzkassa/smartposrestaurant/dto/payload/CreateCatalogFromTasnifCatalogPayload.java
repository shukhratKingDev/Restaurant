package uz.uzkassa.smartposrestaurant.dto.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 11.21.2022 14:55
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateCatalogFromTasnifCatalogPayload implements Serializable {

    String companyId;

    String tin;

    String branchId;

    String creatorId;

    String unitId;
}
