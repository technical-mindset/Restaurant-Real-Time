package com.restaurant.backend.service;


import com.restaurant.backend.helper.PaginationResponse;
import com.restaurant.backend.payloads.BaseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.stream.Collectors;

public abstract class BaseService<E, D extends BaseDTO, R extends JpaRepository<E, Long>> {
    protected final R repository;

    public BaseService(R repository) {
        this.repository = repository;
    }
    public abstract D mapEntityToDto(E entity);
    public abstract E mapDtoToEntity(D dto);
    public PaginationResponse pageToPagination(List<E> entities, Page page){
        PaginationResponse paginationResponse = new PaginationResponse();

        paginationResponse.setData(entities
                .stream()
                .map(e -> this.mapEntityToDto(e))
                .collect(Collectors.toList()));


        paginationResponse.setPageNumber(page.getNumber());
        paginationResponse.setPageSize(page.getSize());
        paginationResponse.setTotalElements(page.getTotalElements());
        paginationResponse.setTotalPages(page.getTotalPages());
        paginationResponse.setLastPage(page.isLast());

        return paginationResponse;
    }
}
