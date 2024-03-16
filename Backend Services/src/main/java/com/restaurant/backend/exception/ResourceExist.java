package com.restaurant.backend.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceExist extends RuntimeException {
    private String resourceName;
    private String fieldName;
    private Object fieldValue;

    public ResourceExist(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s is already exist with %s : %s",resourceName, fieldName, fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }
}
