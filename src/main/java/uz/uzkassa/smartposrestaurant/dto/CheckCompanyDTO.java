package uz.uzkassa.smartposrestaurant.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.uzkassa.smartposrestaurant.dto.address.AddressFullDTO;
import uz.uzkassa.smartposrestaurant.dto.bank.CtsBankDetailDTO;
import uz.uzkassa.smartposrestaurant.dto.company.user.OwnerDTO;
import uz.uzkassa.smartposrestaurant.dto.cts.CtsMerchantDTO;
import uz.uzkassa.smartposrestaurant.enums.BusinessType;
import uz.uzkassa.smartposrestaurant.enums.Vat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 11.21.2022 19:13
 */
@Getter
@Setter
@NoArgsConstructor
public class CheckCompanyDTO implements Serializable {

    Long id;

    String name;

    String apayCompanyId;

    String companyName;

    String tin;

    String phone;

    BusinessType businessType;

    AddressFullDTO address;

    CtsBankDetailDTO bank;

    BigDecimal vatRate;

    Vat vat;

    OwnerDTO owner;

    boolean fromCts;

    boolean fromNic;

    boolean editable;

    List<CtsMerchantDTO> branchList = new ArrayList<>();

    @JsonIgnore
    public void addBranch(CtsMerchantDTO ctsMerchantDTO) {
        branchList.add(ctsMerchantDTO);
    }
}
