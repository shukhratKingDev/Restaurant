package uz.uzkassa.smartposrestaurant.rabbitmq.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import uz.uzkassa.smartposrestaurant.constants.MqConstants;
import uz.uzkassa.smartposrestaurant.dto.payload.UserRolePermissionPayload;
import uz.uzkassa.smartposrestaurant.service.PermissionService;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 27.10.2022 18:48
 */

@Component
@Slf4j
public class RabbitMqPermissionConsumer implements MqConstants {

    private final PermissionService permissionService;

    public RabbitMqPermissionConsumer(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @RabbitListener(
        bindings = @QueueBinding(
            value = @Queue(durable = "false", value = USER_PERMISSION_CREATE_QUEUE),
            exchange = @Exchange(durable = "false", value = DELAY_EXCHANGE_NAME, delayed = "true"),
            key = USER_PERMISSION_CREATE_QUEUE
        ),
        containerFactory = "rabbitListenerContainerFactoryMax"
    )
    public void create(UserRolePermissionPayload userRolePermissionPayload) {
        try {
            permissionService.createUserPermission(userRolePermissionPayload);
        } catch (Exception e) {
            log.error("Create permission by role rabbit mq error: ", e);
            throw new AmqpRejectAndDontRequeueException("Create permission by role rabbit mq error: " + e.getMessage());
        }
    }
}
