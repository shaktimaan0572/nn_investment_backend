package com.example.nnInvestments.listings.entity.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PaginatedResponse {
    Integer currentPage;
    Long totalItem;
    Integer totalPages;
    List<?> responses;
}
