package uz.uzkassa.smartposrestaurant.dto.bank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 10.10.2022 18:45
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BankLookUpDTO implements Serializable {

    String id;

    String name;

    String mfo;
}
