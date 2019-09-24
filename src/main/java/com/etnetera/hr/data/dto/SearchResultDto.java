package com.etnetera.hr.data.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Result DTO of advanced searching
 * @param <TId> Type of ID entity
 * @param <TDto> Type of full DTO
 */
@RequiredArgsConstructor
@Getter
public class SearchResultDto<TId, TDto extends WithId<TId>> {
    /** Total count of result's pages */
    private final int totalPages;
    /** Total elements in the result's page */
    private final long totalElements;
    /** Result's elements (full DTO) */
    private final List<TDto> elements;

    public SearchResultDto() {
        this.totalPages = 0;
        this.totalElements = 0L;
        this.elements = new ArrayList<>(0);

    }
}
