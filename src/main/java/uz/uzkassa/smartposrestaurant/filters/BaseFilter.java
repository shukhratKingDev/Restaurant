package uz.uzkassa.smartposrestaurant.filters;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import uz.uzkassa.smartposrestaurant.enums.SortTypeEnum;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 10.10.2022 18:47
 */
@Getter
@Setter
public class BaseFilter extends CoreFilter implements Serializable {

    protected String id;

    protected String userId;

    protected String parentId;

    protected String companyId;

    protected String branchId;

    protected String regionId;

    protected String districtId;

    protected String categoryId;

    protected LocalDateTime from;

    protected LocalDateTime to;

    protected String warehouseId;

    protected String status;

    protected String tin;

    protected String type;

    public LocalDateTime getFrom() {
        return from != null ? from.with(LocalTime.MIN) : null;
    }

    public LocalDateTime getTo() {
        return to != null ? to.with(LocalTime.MAX) : null;
    }

    public String getSearch() {
        return this.search = this.search != null ? this.search.trim().replaceAll("[.!?,',{,}]", "") : null;
    }

    @JsonIgnore
    public boolean isNotEmpty() {
        return !(search == null || search.length() == 0);
    }

    @JsonIgnore
    public int getStart() {
        return this.getPage() * this.getSize();
    }

    @JsonIgnore
    public Pageable getPageable() {
        return PageRequest.of(this.getPage(), this.getSize());
    }

    @JsonIgnore
    public String getSearchForQuery() {
        return StringUtils.isNotEmpty(search) ? StringUtils.join("%", this.getSearch()) : search;
    }

    @JsonIgnore
    public String getLikeSearchKey() {
        return " like (:searchKey)";
    }

    @JsonIgnore
    public Sort getOrderedSortBy() {
        return sortOrder.equals(SortTypeEnum.asc) ? Sort.by(getDefaultOrderBy()).ascending() : Sort.by(getDefaultOrderBy()).descending();
    }

    @JsonIgnore
    public Pageable getSortedPageable() {
        return PageRequest.of(page, size, getOrderedSortBy());
    }

    @JsonIgnore
    public String getDefaultOrderBy() {
        return "id";
    }

    @JsonIgnore
    public SortTypeEnum getSortOrder() {
        return this.sortOrder != null ? this.sortOrder : SortTypeEnum.desc;
    }
}
