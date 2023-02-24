package uz.uzkassa.smartposrestaurant.dto.company.admin;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import uz.uzkassa.smartposrestaurant.dto.company.CompanyBaseDTO;
import uz.uzkassa.smartposrestaurant.dto.company.user.OwnerDTO;

import java.time.LocalDateTime;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 11.10.2022 16:46
 */
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
public class CompanyListAdminDTO extends CompanyBaseDTO {
    LocalDateTime registrationDate;

    OwnerDTO owner;
}
