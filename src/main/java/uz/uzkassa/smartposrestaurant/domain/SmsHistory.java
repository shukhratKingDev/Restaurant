package uz.uzkassa.smartposrestaurant.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import uz.uzkassa.smartposrestaurant.domain.base.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 26.10.2022 15:23
 */

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "sms_history")
@Entity
@SQLDelete(sql = "UPDATE sms_history SET deleted='true' WHERE id=?")
@Where(clause = "deleted='false'")
public class SmsHistory extends BaseEntity implements Serializable {

    static final long serialVersionUID = 50L;

    @Column(name = "phone")
    String phone;

    @Column(name = "message", columnDefinition = "TEXT")
    String message;

    @Column(name = "code")
    String code;

    @Column(name = "response_message")
    String responseMessage;

    @Column(name = "created_date")
    LocalDateTime createdDate = LocalDateTime.now();

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SmsHistory)) {
            return false;
        }
        return id != null && id.equals(((SmsHistory) o).id);
    }
}
