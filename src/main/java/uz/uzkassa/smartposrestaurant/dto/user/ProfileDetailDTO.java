package uz.uzkassa.smartposrestaurant.dto.user;

import lombok.Getter;
import lombok.Setter;
import uz.uzkassa.smartposrestaurant.dto.file.FileDTO;
import uz.uzkassa.smartposrestaurant.enums.Role;
import uz.uzkassa.smartposrestaurant.enums.Stage;

import java.io.Serializable;
import java.util.List;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 26.10.2022 17:27
 */
@Getter
@Setter
public class ProfileDetailDTO extends ProfileDTO implements Serializable {

    private static final long serialVersionUID = 91224267145509727L;

    String phone;

    Stage stage;

    String companyId;

    FileDTO profileImage;

    List<UserStageDTO> stages;

    Role role;

    String pinfl;
}
