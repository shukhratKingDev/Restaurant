package uz.uzkassa.smartposrestaurant.web.rest.errors;

import lombok.Getter;
import lombok.Setter;
import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 10.10.2022  15:22
 */
@Getter
@Setter
public class NotFoundException extends AbstractThrowableProblem {

    String userMessage;
    String developerMessage;

    public NotFoundException(String userMessage) {
        super(null, userMessage, Status.NOT_FOUND, userMessage);
        this.userMessage = userMessage;
    }

    public NotFoundException(String userMessage, String developerMessage) {
        super(null, userMessage, Status.NOT_FOUND, developerMessage);
        this.userMessage = userMessage;
        this.developerMessage = developerMessage;
    }
}
