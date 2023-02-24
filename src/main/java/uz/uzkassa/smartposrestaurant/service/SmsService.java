package uz.uzkassa.smartposrestaurant.service;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.uzkassa.smartposrestaurant.domain.SmsHistory;
import uz.uzkassa.smartposrestaurant.dto.sms.EtcEntityDTO;
import uz.uzkassa.smartposrestaurant.dto.sms.SmsRequestDTO;
import uz.uzkassa.smartposrestaurant.rabbitmq.producer.RabbitMqSmsSendProducer;
import uz.uzkassa.smartposrestaurant.repository.SmsHistoryRepository;
import uz.uzkassa.smartposrestaurant.utils.SecurityUtils;

import java.util.Optional;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 26.10.2022 14:49
 */
@Slf4j
@Transactional
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class SmsService extends BaseService {

    String SENT_JSON_REQUEST = "/json2sms";
    SmsHistoryRepository smsHistoryRepository;
    RabbitMqSmsSendProducer rabbitMqSmsSendProducer;

    public void sendQueue(SmsRequestDTO smsRequestDTO) {
        rabbitMqSmsSendProducer.send(smsRequestDTO);
    }

    @Transactional(readOnly = true)
    public Optional<SmsHistory> getByPhoneAndCode(String phone, String code) {
        return smsHistoryRepository.findFirstByPhoneAndCodeOrderByCreatedDateDesc(phone, code);
    }

    public void send(SmsRequestDTO dto) {
        String phoneNumber = dto.getPhone().replace("+", "").trim();
        String message = dto.getCode();
        if (StringUtils.isEmpty(message)) {
            return;
        }
        if (phoneNumber.startsWith("99898")) {
            message = message + " \na-pay.uz";
        }

        EtcEntityDTO.EtcEntityDTOBuilder builder = EtcEntityDTO
            .builder()
            .login(applicationProperties.getSmsConfig().getUsername())
            .pwd(applicationProperties.getSmsConfig().getPassword())
            .cdPN(phoneNumber)
            .cgPN(applicationProperties.getSmsConfig().getCgpn())
            .text(message);
        try {
            HttpEntity<EtcEntityDTO> entity = new HttpEntity<>(builder.build(), SecurityUtils.getHeader());
            restTemplate.exchange(applicationProperties.getSmsConfig().getHost() + SENT_JSON_REQUEST, HttpMethod.POST, entity, String.class);
            saveSms(phoneNumber, dto.getCode(), message, null);
        } catch (Exception e) {
            log.error(e.getMessage());
            saveSms(phoneNumber, dto.getCode(), message, e.getMessage());
        }
    }

    private void saveSms(String phone, String code, String message, String responseMessage) {
        SmsHistory history = new SmsHistory();
        history.setPhone(phone);
        history.setMessage(message);
        history.setCode(code);
        history.setResponseMessage(responseMessage);
        smsHistoryRepository.save(history);
    }
}
