package com.blogapp.BlogApp1.exception;
import java.util.Objects;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    private String resourceName;
    private String fieldName;
    private long fieldValue;

    // public ResourceNotFoundException() {
    // }

    public ResourceNotFoundException(String resourceName, String fieldName, long fieldValue) {
        super(String.format("%s not found with %s: '%s'",resourceName,fieldName,fieldValue)); //PostNotFoundWithId
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    public String getResourceName() {
        return this.resourceName;
    }

    // public void setResourceName(String resourceName) {
    //     this.resourceName = resourceName;
    // }

    public String getFieldName() {
        return this.fieldName;
    }

    // public void setFieldName(String fieldName) {
    //     this.fieldName = fieldName;
    // }

    public long getFieldValue() {
        return this.fieldValue;
    }

    // public void setFieldValue(String fieldValue) {
    //     this.fieldValue = fieldValue;
    // }

    // public ResourceNotFoundException resourceName(String resourceName) {
    //     setResourceName(resourceName);
    //     return this;
    // }

    // public ResourceNotFoundException fieldName(String fieldName) {
    //     setFieldName(fieldName);
    //     return this;
    // }

    // public ResourceNotFoundException fieldValue(String fieldValue) {
    //     setFieldValue(fieldValue);
    //     return this;
    // }

    // @Override
    // public boolean equals(Object o) {
    //     if (o == this)
    //         return true;
    //     if (!(o instanceof ResourceNotFoundException)) {
    //         return false;
    //     }
    //     ResourceNotFoundException resourceNotFoundException = (ResourceNotFoundException) o;
    //     return Objects.equals(resourceName, resourceNotFoundException.resourceName) && Objects.equals(fieldName, resourceNotFoundException.fieldName) && Objects.equals(fieldValue, resourceNotFoundException.fieldValue);
    // }

    // @Override
    // public int hashCode() {
    //     return Objects.hash(resourceName, fieldName, fieldValue);
    // }

    // @Override
    // public String toString() {
    //     return "{" +
    //         " resourceName='" + getResourceName() + "'" +
    //         ", fieldName='" + getFieldName() + "'" +
    //         ", fieldValue='" + getFieldValue() + "'" +
    //         "}";
    // }

}
