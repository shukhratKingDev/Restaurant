package uz.uzkassa.smartposrestaurant.dto.sms;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 26.10.2022 14:51
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class SmsRequestDTO implements Serializable {

    @NotNull
    String phone;

    String code;
}
