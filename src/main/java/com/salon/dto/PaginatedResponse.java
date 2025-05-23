package com.salon.dto;


import lombok.Data;

@Data
public class PaginatedResponse<T> {
    private T data;
    private PaginationInfo pagination;
   
}
