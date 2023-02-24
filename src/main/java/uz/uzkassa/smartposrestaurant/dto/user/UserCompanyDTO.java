package uz.uzkassa.smartposrestaurant.dto.user;

import lombok.Getter;
import lombok.Setter;
import uz.uzkassa.smartposrestaurant.dto.base.CommonDTO;
import uz.uzkassa.smartposrestaurant.enums.CommonStatus;

import java.io.Serializable;

/**
 * Powered by: Shuxratjon Rayimjonov
 */
@Getter
@Setter
public class UserCompanyDTO extends CommonDTO implements Serializable {

    private static final long serialVersionUID = 5122626775809722737L;

    private String tin;

    private CommonStatus status;

}
