package com.restaurant.backend.model;

import jakarta.persistence.Column;

import jakarta.persistence.MappedSuperclass;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@MappedSuperclass
public class BaseEntity {

    @Column(name = "createdBy")
    private String createdBy;
    @Column(name = "updatedBy")
    private String updatedBy;
    @Column(name = "createdAt")
    private LocalDateTime createdAt;
    @Column(name = "updatedAt")
    private LocalDateTime updatedAt;

    public BaseEntity(){}

}
