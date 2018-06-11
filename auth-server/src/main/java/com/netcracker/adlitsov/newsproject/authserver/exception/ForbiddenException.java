package com.netcracker.adlitsov.newsproject.authserver.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class ForbiddenException extends RuntimeException {
    private String resourceName;
    private String fieldName;
    private Object fieldValue;
    private int accessorId;

    public ForbiddenException( String resourceName, String fieldName, Object fieldValue, int accessorId) {
        super(String.format("%s with %s : '%s' is forbidden for user with id %d",
                            resourceName, fieldName, fieldValue, accessorId));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
        this.accessorId = accessorId;
    }

    public ForbiddenException() {

    }

    public String getResourceName() {
        return resourceName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public Object getFieldValue() {
        return fieldValue;
    }

    public int getAccessorId() {
        return accessorId;
    }
}

