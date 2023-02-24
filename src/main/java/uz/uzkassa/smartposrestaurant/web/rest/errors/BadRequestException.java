package uz.uzkassa.smartposrestaurant.web.rest.errors;

import lombok.Getter;
import lombok.Setter;
import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 07.10.2022
 */
@Getter
@Setter
public class BadRequestException extends AbstractThrowableProblem {

    Object userMessage;
    String developerMessage;
    Object date;

    public BadRequestException(String userMessage) {
        super(null,userMessage, Status.BAD_REQUEST,userMessage);
        this.userMessage = userMessage;
    }

    public BadRequestException(String userMessage, String developerMessage) {
        super(null,userMessage,Status.BAD_REQUEST,developerMessage);
        this.userMessage = userMessage;
        this.developerMessage = developerMessage;
    }

    public BadRequestException(String userMessage, String developerMessage, Object date) {
        super(null,userMessage,Status.BAD_REQUEST,userMessage);
        this.userMessage = userMessage;
        this.developerMessage = developerMessage;
        this.date = date;
    }
}
