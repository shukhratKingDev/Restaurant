package uz.uzkassa.smartposrestaurant.integration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import uz.uzkassa.smartposrestaurant.dto.soliq.MxikDTO;
import uz.uzkassa.smartposrestaurant.dto.soliq.MxikResponseDTO;
import uz.uzkassa.smartposrestaurant.service.BaseService;
import uz.uzkassa.smartposrestaurant.utils.SecurityUtils;

import java.util.List;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 11.21.2022 15:42
 */
@Component
@Slf4j
@Lazy
public class FacturaClient extends BaseService {

    public List<MxikDTO> getCompanyCatalog(String tin) {
        HttpHeaders headers = SecurityUtils.getHeader();
        HttpEntity<?> entity = new HttpEntity<>(headers);

        String url = applicationProperties.getTasnifConfig().getUrl();
        if (tin.length() == 9) {
            url = url + "&tin=" + tin;
        } else {
            url = url + "&pinfl=" + tin;
        }
        ResponseEntity<MxikResponseDTO> response = restTemplate.exchange(
            url,
            HttpMethod.GET,
            entity,
            MxikResponseDTO.class);
        return response.getBody().getData();
    }
}
