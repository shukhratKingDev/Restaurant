package uz.uzkassa.smartposrestaurant.constants;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 26.10.2022 16:13
 */
public interface MqConstants {

    String DELAY_EXCHANGE_NAME = "x-delayed-message";

    String X_DELAY = "x-delay";
    String TYPE_ID = "__TypeId__";

    Integer DEFAULT_DELAY = 1000;

    String SMS_SEND_QUEUE = "SMS_SEND_QUEUE";
    String SMS_SEND_PAYLOAD = "SMS_SEND_PAYLOAD";

    String USER_PERMISSION_CREATE_QUEUE = "USER_PERMISSION_CREATE_QUEUE";
    String USER_PERMISSION_CREATE_PAYLOAD = "USER_PERMISSION_CREATE_PAYLOAD";

    String CREATE_CATALOG_FROM_TASNIF_CATALOG_QUEUE = "CREATE_CATALOG_FROM_TASNIF_CATALOG_QUEUE";
    String CREATE_CATALOG_FROM_TASNIF_CATALOG_PAYLOAD = "CREATE_CATALOG_FROM_TASNIF_CATALOG_PAYLOAD";


}
