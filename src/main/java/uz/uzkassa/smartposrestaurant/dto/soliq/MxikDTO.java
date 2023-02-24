package uz.uzkassa.smartposrestaurant.dto.soliq;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.List;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.StringUtils;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 18.10.2022 16:39
 */
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MxikDTO implements Serializable {

    String pkey;

    String tin;

    String mxikCode;

    String groupName;

    String className;

    String positionName;

    String subPositionName;

    String brandName;

    String attributeName;

    String internationalCode;

    List<PackageDTO> packageNames;

    public String getName() {
        String name = StringUtils.defaultString(getSubPositionName(), "");
        if (getBrandName() != null) {
            name += " " + getBrandName();
        }
        if (getAttributeName() != null) {
            name += " " + getAttributeName();
        }
        return StringUtils.isNotEmpty(name) ? name : null;
    }
}
