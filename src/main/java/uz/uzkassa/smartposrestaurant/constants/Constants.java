package uz.uzkassa.smartposrestaurant.constants;

/**
 * Application constants.
 */
public interface Constants {

    String LOGIN_REGEX = "^(?>[a-zA-Z0-9!$&*+=?^_`{|}~.-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*)|(?>[_.@A-Za-z0-9-]+)$";
    String PHONE_REGEX = "^998[\\d]{9}$";
    String SYSTEM = "system";
    String DEFAULT_LANGUAGE = "en";
    String TIN_REGEX = "[0-9]{9,14}";
    String ACTIVATION_KEY_REGEX = "[\\d]{6}$";
}
