package uz.uzkassa.smartposrestaurant.web.rest.errors;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 10.10.2022 15:20
 */
public class IntegrationException extends RuntimeException {

    ErrorDto error;

    public IntegrationException() {
    }

    public IntegrationException(String message) {
        super(message);
    }

    public IntegrationException(ErrorDto error) {
        this.error = error;
    }

    public IntegrationException(String message, ErrorDto error) {

    }

    public ErrorDto getError() {
        return error;
    }

    public void setError(ErrorDto error) {
        this.error = error;
    }
}
