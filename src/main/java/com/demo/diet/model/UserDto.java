package com.demo.diet.model;

import com.demo.diet.entity.User;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class UserDto {

    @NotEmpty(message = "The name cannot be empty.")
    @Size(min = 2, max = 100, message = "The length of full name must be between 2 and 100 characters.")
    private String name;

    @NotEmpty(message = "The email cannot be empty.")
    @Email(message = "Email should be valid")
    private String email;

    public User toUser() {
        return new User()
                .setName(name)
                .setEmail(email);
    }
}
