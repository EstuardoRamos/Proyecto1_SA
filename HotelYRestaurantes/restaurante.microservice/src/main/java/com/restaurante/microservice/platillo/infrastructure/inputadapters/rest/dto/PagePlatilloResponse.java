package com.restaurante.microservice.platillo.infrastructure.inputadapters.rest.dto;

import org.springframework.data.domain.Page;

import java.util.List;

public record PagePlatilloResponse<T>(
        List<T> content,
        int page, int size, long totalElements, int totalPages, boolean first, boolean last
        ) {

    public static <T> PagePlatilloResponse<T> from(Page<T> p) {
        return new PagePlatilloResponse<>(
                p.getContent(), p.getNumber(), p.getSize(), p.getTotalElements(),
                p.getTotalPages(), p.isFirst(), p.isLast()
        );
    }
}
