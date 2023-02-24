package uz.uzkassa.smartposrestaurant.dto.soliq;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 10.10.2022 16:54
 */
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class TaxCompanyDTO implements Serializable {

    String ns10Code;

    String ns11Code;

    String tin;

    String fullName;

    String name;

    String mfo;

    String account;

    String oked;

    String director;

    String directorTin;

    String accountant;

    public String getName() {
        return StringUtils.isNotEmpty(name) ? name : fullName;
    }
}
