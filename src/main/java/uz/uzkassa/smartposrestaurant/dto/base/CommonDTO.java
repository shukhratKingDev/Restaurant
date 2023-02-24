package uz.uzkassa.smartposrestaurant.dto.base;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.Objects;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 10.10.2022 20:13
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PROTECTED)
public class CommonDTO implements Serializable {

    String id;

    String name;

    public CommonDTO(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CommonDTO)) {
            return false;
        }
        return Objects.equals(id, ((CommonDTO) o).id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
