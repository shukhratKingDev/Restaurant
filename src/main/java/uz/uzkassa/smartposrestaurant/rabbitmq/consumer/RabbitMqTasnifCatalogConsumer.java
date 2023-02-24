package uz.uzkassa.smartposrestaurant.rabbitmq.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import uz.uzkassa.smartposrestaurant.constants.MqConstants;
import uz.uzkassa.smartposrestaurant.dto.payload.CreateCatalogFromTasnifCatalogPayload;
import uz.uzkassa.smartposrestaurant.service.CategoryService;
import uz.uzkassa.smartposrestaurant.service.ProductService;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 11.22.2022 15:29
 */
@Component
@Slf4j
public class RabbitMqTasnifCatalogConsumer implements MqConstants {

    private final CategoryService categoryService;


    public RabbitMqTasnifCatalogConsumer(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @RabbitListener(
        bindings = @QueueBinding(
            value = @Queue(durable = "false", value = CREATE_CATALOG_FROM_TASNIF_CATALOG_QUEUE),
            exchange = @Exchange(durable = "false", value = DELAY_EXCHANGE_NAME, delayed = "true"),
            key = CREATE_CATALOG_FROM_TASNIF_CATALOG_QUEUE
        ),
        containerFactory = "rabbitListenerContainerFactoryMax"
    )
    public void createCatalogFromTasnifCatalogQueue(CreateCatalogFromTasnifCatalogPayload createCatalogFromTasnifCatalogPayload) {
        try {
            categoryService.createCatalogFromTasnifCatalogQueue(createCatalogFromTasnifCatalogPayload);
        } catch (Exception e) {
            log.error("Create tasnif catalog queue error: ", e);
            throw new AmqpRejectAndDontRequeueException("Create tasnif catalog queue error: " + e.getMessage());
        }
    }
}
