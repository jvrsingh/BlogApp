package com.blogapp.BlogApp1.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class CommentDto {

    private long id;
    @NotEmpty(message = "Name should not be empaty")
    private String name;

    @Email
    @NotEmpty(message = "emaild should not be empaty")
    private String email;

    @NotEmpty
    @Size(min = 10, message = "Comment Body should be not null and be more than 10 character")
    private String body;

}
