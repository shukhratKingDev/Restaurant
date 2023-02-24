package uz.uzkassa.smartposrestaurant.web.rest.errors;

import java.io.Serializable;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 10.10.2022 15:16
 */
public class FieldErrorVm implements Serializable {

    private static final long  serialVersionUID = 1L;

    private final String objectName;

    private final String field;

    private final String message;

    public FieldErrorVm(String dto, String field, String message) {
        this.objectName = dto;
        this.field = field;
        this.message = message;
    }

    public String getObjectName() {
        return objectName;
    }

    public String getField() {
        return field;
    }

    public String getMessage() {
        return message;
    }
}
