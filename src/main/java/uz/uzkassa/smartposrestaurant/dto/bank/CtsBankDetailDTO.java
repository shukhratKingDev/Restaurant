package uz.uzkassa.smartposrestaurant.dto.bank;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 11.21.2022 19:19
 */
@Getter
@Setter
@NoArgsConstructor
public class CtsBankDetailDTO implements Serializable {

    String id;

    String name;

    String accountNumber;

    String mfo;

    String oked;

    String tin;

    CtsBankDetailDTO parent;

}
