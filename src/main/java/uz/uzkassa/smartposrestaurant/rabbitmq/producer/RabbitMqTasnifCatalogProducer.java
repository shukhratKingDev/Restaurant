package uz.uzkassa.smartposrestaurant.rabbitmq.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessagePropertiesBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import uz.uzkassa.smartposrestaurant.constants.MqConstants;
import uz.uzkassa.smartposrestaurant.dto.payload.CreateCatalogFromTasnifCatalogPayload;

@Component
@Slf4j
public class RabbitMqTasnifCatalogProducer implements MqConstants {

    private final RabbitTemplate rabbitTemplate;

    private final ObjectMapper objectMapper;

    public RabbitMqTasnifCatalogProducer(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    public void createCatalogFromTasnifCatalogQueue(CreateCatalogFromTasnifCatalogPayload payload, Integer delay) {
        String payloadString;
        try {
            payloadString = objectMapper.writeValueAsString(payload);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
            return;
        }

        Message jsonMessage = MessageBuilder
            .withBody(payloadString.getBytes())
            .andProperties(
                MessagePropertiesBuilder
                    .newInstance()
                    .setContentType(MediaType.APPLICATION_JSON_VALUE)
                    .setHeader(X_DELAY, delay == null ? DEFAULT_DELAY : (1000 * delay))
                    .setHeader(TYPE_ID, CREATE_CATALOG_FROM_TASNIF_CATALOG_PAYLOAD)
                    .build()
            )
            .build();
        rabbitTemplate.send(DELAY_EXCHANGE_NAME, CREATE_CATALOG_FROM_TASNIF_CATALOG_QUEUE, jsonMessage);
    }

    public void createCatalogFromTasnifCatalogQueue(CreateCatalogFromTasnifCatalogPayload payload) {
        createCatalogFromTasnifCatalogQueue(payload, null);
    }

}
