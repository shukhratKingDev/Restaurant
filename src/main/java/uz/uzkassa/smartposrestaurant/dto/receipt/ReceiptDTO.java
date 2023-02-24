package uz.uzkassa.smartposrestaurant.dto.receipt;

import java.io.Serializable;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import uz.uzkassa.smartposrestaurant.dto.base.CommonDTO;
import uz.uzkassa.smartposrestaurant.dto.branch.admin.BranchAdminDTO;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 18.10.2022 17:50
 */
@Getter
@Setter
public class ReceiptDTO implements Serializable {

    String id;

    CommonDTO company;

    BranchAdminDTO branch;

    List<ReceiptItemDTO> items;
}
