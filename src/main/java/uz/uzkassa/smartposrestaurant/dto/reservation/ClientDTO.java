package uz.uzkassa.smartposrestaurant.dto.reservation;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 11.29.2022 14:30
 */
@Getter
@Setter
@NoArgsConstructor
public class ClientDTO {

    String id;

    String phone;

    String firstName;

    String lastName;

    public ClientDTO(String id, String phone, String firstName, String lastName) {
        this.id = id;
        this.phone = phone;
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
