package uz.uzkassa.smartposrestaurant.web.rest.errors;

import lombok.Getter;
import lombok.Setter;
import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 10.10.2022 15:06
 */
@Getter
@Setter
public class EntityAlreadyExistException extends AbstractThrowableProblem {
    String userMessage;
    String developerMessage;

    public EntityAlreadyExistException(String userMessage) {
        super(null, userMessage, Status.CONFLICT, userMessage);
        this.userMessage = userMessage;
    }

    public EntityAlreadyExistException(String userMessage, String developerMessage) {
        super(null, userMessage, Status.CONFLICT, developerMessage);
        this.userMessage = userMessage;
        this.developerMessage = developerMessage;
    }
}
