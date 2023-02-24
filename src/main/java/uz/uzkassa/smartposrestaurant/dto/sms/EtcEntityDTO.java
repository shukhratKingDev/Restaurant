package uz.uzkassa.smartposrestaurant.dto.sms;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 27.10.2022 15:43
 */
@Getter
@Setter
@Builder
public class EtcEntityDTO {

    private String login;
    private String pwd;

    @JsonProperty("CgPN")
    private String cgPN;

    @JsonProperty("CdPN")
    private String cdPN;

    String text;
}
