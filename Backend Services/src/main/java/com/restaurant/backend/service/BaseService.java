package com.restaurant.backend.service;


import com.restaurant.backend.dao.UserRepository;
import com.restaurant.backend.helper.PaginationResponse;
import com.restaurant.backend.model.User;
import com.restaurant.backend.payloads.BaseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.stream.Collectors;

public abstract class BaseService<E, D extends BaseDTO, R extends JpaRepository<E, Long>> {
    protected final R repository;

    @Autowired
    private UserRepository userRepository;

    public BaseService(R repository) {
        this.repository = repository;
    }
    public abstract D mapEntityToDto(E entity);
    public abstract E mapDtoToEntity(D dto);
    public PaginationResponse pageToPagination(Pageable pageable){

        Page page = this.repository.findAll(pageable);
        List<E> entities = page.getContent();

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

    // Get user info
    public String getUserName(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = this.userRepository.findByUsername(authentication.getName()).get();
//        User user = this.userRepository.findByUsername("shahid").get();
        return user.getUsername();
    }
}
