package com.salon.dto;

import lombok.Data;

@Data
public class PaginationInfo {
    private int currentPage;
    private int pageSize;
    private int totalElements;
    private int totalPages;
    private boolean hasNext;
    private boolean hasPrevious;
    private String sortBy;
    private String sortDirection;

}
