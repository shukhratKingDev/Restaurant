package uz.uzkassa.smartposrestaurant.dto.fileUpload;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import uz.uzkassa.smartposrestaurant.dto.base.CommonDTO;

import java.io.Serializable;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 26.10.2022 20:19
 */
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BaseFileUploadDTO extends CommonDTO implements Serializable {

    String path;

    String url;
}
