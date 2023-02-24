package uz.uzkassa.smartposrestaurant.dto.base;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 10.10.2022 17:50
 */
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PagedResponseDTO<T> implements Serializable {

    String totalElements;

    String totalPages;

    String numberOfElements;

    String size;

    String number;

    List<T> content=new ArrayList<>();
}
