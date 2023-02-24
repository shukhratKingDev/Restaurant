package uz.uzkassa.smartposrestaurant.dto.base;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 10.10.2022 19:17
 */
@Getter
@Setter
@NoArgsConstructor
public class ResultList<T> implements Serializable {

    private List<T> list = new ArrayList<>();

    private Long count = 0L;

    public ResultList(List<T> list, Long count) {
        this.list = list;
        this.count = count;
    }
}
