package uz.uzkassa.smartposrestaurant.dto.soliq;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 11.22.2022 16:34
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class MxikResponseDTO implements Serializable {

    private List<MxikDTO> data;
}
