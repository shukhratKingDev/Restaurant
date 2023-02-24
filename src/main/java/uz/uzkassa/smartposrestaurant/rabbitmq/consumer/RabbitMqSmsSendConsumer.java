package uz.uzkassa.smartposrestaurant.rabbitmq.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import uz.uzkassa.smartposrestaurant.constants.MqConstants;
import uz.uzkassa.smartposrestaurant.dto.sms.SmsRequestDTO;
import uz.uzkassa.smartposrestaurant.service.SmsService;

/**
 * Powered by: Rayimjonov Shuxratjon
 * Date: 26.08.2021 21:43
 */

@Component
@Slf4j
public class RabbitMqSmsSendConsumer implements MqConstants {

    private final SmsService smsService;

    public RabbitMqSmsSendConsumer(SmsService smsService) {
        this.smsService = smsService;
    }

    @RabbitListener(
        bindings = @QueueBinding(
            value = @Queue(durable = "false", value = SMS_SEND_QUEUE),
            exchange = @Exchange(durable = "false", value = DELAY_EXCHANGE_NAME, delayed = "true"),
            key = SMS_SEND_QUEUE
        ),
        containerFactory = "rabbitListenerContainerFactoryMax"
    )
    public void send(SmsRequestDTO smsRequestDTO) {
        try {
            smsService.send(smsRequestDTO);
        } catch (Exception e) {
            log.error("Send sms rabbit mq error: ", e);
            throw new AmqpRejectAndDontRequeueException("Send sms rabbit mq error: " + e.getMessage());
        }
    }
}
