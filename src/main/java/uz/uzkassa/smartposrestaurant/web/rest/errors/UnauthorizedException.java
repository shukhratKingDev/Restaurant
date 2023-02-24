package uz.uzkassa.smartposrestaurant.web.rest.errors;

import lombok.Getter;
import lombok.Setter;
import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 26.10.2022 18:42
 */
@Getter
@Setter
public class UnauthorizedException extends AbstractThrowableProblem {

    String userMessage;

    String developerMessage;

    public UnauthorizedException(String userMessage) {
        super(null, userMessage, Status.UNAUTHORIZED, userMessage);
        this.userMessage = userMessage;
    }

    public UnauthorizedException(String userMessage, String developerMessage) {
        super(null, userMessage, Status.UNAUTHORIZED, developerMessage);
        this.userMessage = userMessage;
        this.developerMessage = developerMessage;
    }
}
