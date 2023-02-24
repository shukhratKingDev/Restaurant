package uz.uzkassa.smartposrestaurant.integration;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import uz.uzkassa.smartposrestaurant.config.ApplicationProperties;
import uz.uzkassa.smartposrestaurant.dto.soliq.TaxCompanyDTO;
import uz.uzkassa.smartposrestaurant.utils.SecurityUtils;
import uz.uzkassa.smartposrestaurant.web.rest.errors.IntegrationException;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 10.10.2022 16:51
 */
@Component
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
public class SoliqClient {

    ApplicationProperties applicationProperties;
    RestTemplate restTemplate;

    public TaxCompanyDTO getCustomerByTin(String tin) {
        return tin.startsWith("3") || tin.startsWith("2") ? getCompanyByTin(tin) : getUserByTin(tin);
    }

    private TaxCompanyDTO getCompanyByTin(String tin) {
        String lang = "ru";

        HttpEntity<?> entity = new HttpEntity<>(getHeaders());
        try{
            ResponseEntity<TaxCompanyDTO>response=restTemplate.exchange(
                applicationProperties.getSoliqConfig().getHost()+"np1/bytin/factura?lang=" + lang + "&tin=" + tin,
                HttpMethod.GET,
                entity,
                TaxCompanyDTO.class
            );
            if (response.getStatusCode()!= HttpStatus.OK){
                throw new IntegrationException("Bad Request");
            }
            return response.getBody();
        } catch (Exception e){
            log.error(e.getMessage());
            if (e instanceof IntegrationException){
                throw new IntegrationException(e.getMessage(),((IntegrationException)e).getError());
            }
            throw new IntegrationException("Bad Request");
        }
    }

    public TaxCompanyDTO getUserByTin(String tin){
        String lang="ru";
        HttpEntity<?> entity=new HttpEntity<>(getHeaders());

        try{
            ResponseEntity<TaxCompanyDTO>response=restTemplate.exchange(
                applicationProperties.getSoliqConfig().getHost()+"np1/phisbytin/factura?lang=" + lang + "&tin=" + tin,
                HttpMethod.GET,
                entity,
                TaxCompanyDTO.class
            );
            if (response.getStatusCode()!=HttpStatus.OK){
                throw new IntegrationException("Bad Request");
            }
            return response.getBody();
        }catch (Exception e){
            log.error(e.getMessage());
            if (e instanceof IntegrationException){
                throw new IntegrationException(e.getMessage(),((IntegrationException)e).getError());
            }
            throw new IntegrationException("Bad Request");
        }
    }

    private HttpHeaders getHeaders() {
        HttpHeaders headers = SecurityUtils.getHeader();
        headers.setBasicAuth(applicationProperties.getSoliqConfig().getUsername(), applicationProperties.getSoliqConfig().getPassword());
        return headers;
    }
}
