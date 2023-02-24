package uz.uzkassa.smartposrestaurant.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.uzkassa.smartposrestaurant.enums.Stage;

import java.io.Serializable;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 27.10.2022 14:30
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserStageDTO implements Serializable {

    Stage stage;

    boolean hasPassed;
}
