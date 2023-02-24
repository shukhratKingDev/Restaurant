package uz.uzkassa.smartposrestaurant.rabbitmq.producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessagePropertiesBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import uz.uzkassa.smartposrestaurant.constants.MqConstants;
import uz.uzkassa.smartposrestaurant.dto.payload.UserRolePermissionPayload;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 27.10.2022 16:47
 */
@Component
@Slf4j
public class RabbitMqPermissionProducer implements MqConstants {

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    @Autowired
    public RabbitMqPermissionProducer(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    public void save(UserRolePermissionPayload userRolePermissionPayload) {
        String payloadString;
        try {
            payloadString = objectMapper.writeValueAsString(userRolePermissionPayload);
        } catch (Exception e) {
            log.error(e.getMessage());
            return;
        }

        Message jsonMessage = MessageBuilder
            .withBody(payloadString.getBytes())
            .andProperties(
                MessagePropertiesBuilder
                    .newInstance()
                    .setContentType(MediaType.APPLICATION_JSON_VALUE)
                    .setHeader(X_DELAY, DEFAULT_DELAY)
                    .setHeader(TYPE_ID, USER_PERMISSION_CREATE_PAYLOAD)
                    .build()
            )
            .build();
        rabbitTemplate.send(DELAY_EXCHANGE_NAME, USER_PERMISSION_CREATE_QUEUE, jsonMessage);
    }
}
