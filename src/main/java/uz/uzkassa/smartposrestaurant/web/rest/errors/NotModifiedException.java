package uz.uzkassa.smartposrestaurant.web.rest.errors;

import lombok.Getter;
import lombok.Setter;
import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 10.10.2022 15:25
 */
@Getter
@Setter
public class NotModifiedException extends AbstractThrowableProblem {

    String userMessage;
    String developerMessage;
    Object date;

    public NotModifiedException(String userMessage) {
        super(null, userMessage, Status.NOT_MODIFIED, userMessage);
        this.userMessage = userMessage;
    }

    public NotModifiedException(String userMessage, String developerMessage) {
        super(null, userMessage, Status.BAD_REQUEST, developerMessage);
        this.userMessage = userMessage;
        this.developerMessage = developerMessage;
    }

    public NotModifiedException(String userMessage, String developerMessage, Object date) {
        super(null, userMessage, Status.BAD_REQUEST, developerMessage);
        this.userMessage = userMessage;
        this.developerMessage = developerMessage;
        this.date = date;
    }
}
