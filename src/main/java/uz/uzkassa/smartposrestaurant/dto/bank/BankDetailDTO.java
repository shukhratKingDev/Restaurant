package uz.uzkassa.smartposrestaurant.dto.bank;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 11.10.2022 17:25
 */
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
public class BankDetailDTO implements Serializable {

    String id;

    String name;

    String accountNumber;

    String mfo;

    String oked;
}
