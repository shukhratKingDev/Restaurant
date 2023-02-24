package uz.uzkassa.smartposrestaurant.dto.user;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 27.10.2022 14:51
 */
@Getter
@Setter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserIdDTO implements Serializable {

    String id;
}
