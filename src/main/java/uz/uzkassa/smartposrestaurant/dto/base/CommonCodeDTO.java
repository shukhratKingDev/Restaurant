package uz.uzkassa.smartposrestaurant.dto.base;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Objects;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 11.10.2022 14:55
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@FieldDefaults(level = AccessLevel.PROTECTED)
public class CommonCodeDTO extends CommonDTO {
    String code;

    public CommonCodeDTO(String id, String name, String code) {
        super(id, name);
        this.code = code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CommonCodeDTO)) {
            return false;
        }
        return Objects.equals(id, ((CommonCodeDTO) o).id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
