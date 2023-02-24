package uz.uzkassa.smartposrestaurant.web.rest.errors;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 10.10.2022 15:13
 */

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ErrorDto implements Serializable {

    String timeStamp;

    int status;

    String error;

    String message;

    String path;

    String title;

    String detail;
}
