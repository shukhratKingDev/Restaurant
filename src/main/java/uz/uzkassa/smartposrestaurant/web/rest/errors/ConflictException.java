package uz.uzkassa.smartposrestaurant.web.rest.errors;

import lombok.Getter;
import lombok.Setter;
import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 07.10.2022 20:33
 */

@Getter
@Setter
public class ConflictException extends AbstractThrowableProblem {
    String userMessage;
    String developerMessage;

    public ConflictException(String userMessage) {
        super(null,userMessage, Status.CONFLICT,userMessage);
        this.userMessage = userMessage;
    }

    public ConflictException(String userMessage, String developerMessage) {
        super(null,userMessage,Status.CONFLICT,developerMessage);
        this.userMessage = userMessage;
        this.developerMessage = developerMessage;
    }

}
