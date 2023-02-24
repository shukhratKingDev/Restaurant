package uz.uzkassa.smartposrestaurant.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import uz.uzkassa.smartposrestaurant.dto.address.AddressLocalizedDTO;
import uz.uzkassa.smartposrestaurant.dto.bank.CtsBankDetailDTO;
import uz.uzkassa.smartposrestaurant.dto.company.user.OwnerDTO;
import uz.uzkassa.smartposrestaurant.dto.cts.CtsBranchDTO;
import uz.uzkassa.smartposrestaurant.enums.BusinessType;
import uz.uzkassa.smartposrestaurant.enums.Vat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 11.21.2022 19:18
 */

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CtsCustomerDetailDTO implements Serializable {

    Long id;

    String name;

    String companyName;

    String tin;

    String phone;

    CtsActivityTypeDTO activityTypeDTO;

    BusinessType businessType;

    AddressLocalizedDTO address;

    CtsBankDetailDTO bank;

    BigDecimal vatRate;

    OwnerDTO owner;

    Vat vat;

    boolean fromCts;

    boolean fromNic;

    boolean editable;

    List<CtsBranchDTO> branchList = new ArrayList<>();

    @JsonIgnore
    public void addBranch(CtsBranchDTO ctsBranchDTO) {
        branchList.add(ctsBranchDTO);
    }


}
