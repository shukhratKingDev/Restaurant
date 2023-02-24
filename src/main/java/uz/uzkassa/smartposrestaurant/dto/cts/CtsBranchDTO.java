package uz.uzkassa.smartposrestaurant.dto.cts;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.uzkassa.smartposrestaurant.dto.CtsActivityTypeDTO;
import uz.uzkassa.smartposrestaurant.dto.address.AddressLocalizedDTO;

import java.io.Serializable;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 11.21.2022 20:03
 */
@Getter
@Setter
@NoArgsConstructor
public class CtsBranchDTO implements Serializable {

    Long id;

    String name;

    String phone;

    AddressLocalizedDTO address;

    CtsActivityTypeDTO activityType;
}
