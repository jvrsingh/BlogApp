package com.blogapp.BlogApp1.exception;

import org.springframework.http.HttpStatus;


public class BlogAPIException extends RuntimeException{

    private HttpStatus status;
    private String message;

    public BlogAPIException() {
    }

    public BlogAPIException(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return this.status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public BlogAPIException status(HttpStatus status) {
        setStatus(status);
        return this;
    }

    public BlogAPIException message(String message) {
        setMessage(message);
        return this;
    }

    // @Override
    // public boolean equals(Object o) {
    //     if (o == this)
    //         return true;
    //     if (!(o instanceof BlogAPIException)) {
    //         return false;
    //     }
    //     BlogAPIException blogAPIException = (BlogAPIException) o;
    //     return Objects.equals(status, blogAPIException.status) && Objects.equals(message, blogAPIException.message);
    // }

    // @Override
    // public int hashCode() {
    //     return Objects.hash(status, message);
    // }

    // @Override
    // public String toString() {
    //     return "{" +
    //         " status='" + getStatus() + "'" +
    //         ", message='" + getMessage() + "'" +
    //         "}";
    // }

    
    
}
