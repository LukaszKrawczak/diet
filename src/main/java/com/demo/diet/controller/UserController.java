package com.demo.diet.controller;

import com.demo.diet.service.UserService;
import com.demo.diet.entity.User;
import com.demo.diet.model.UserDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping(path = "/api")
public class UserController {

    private UserService userService;

    @RequestMapping(path = "/users")
    public List<User> getUsers() {
        return userService.getAllUsers();
    }

    @PostMapping(value = "/add-user")
    public ResponseEntity<Map<String, String>> putUser(@Valid @RequestBody UserDto userDto) {
        User addedUser = userDto.toUser();
        userService.saveUser(addedUser);
        Map<String, String> map = new HashMap<>();
        map.put("message", "User " + addedUser.getName() + " saved.");
        return new ResponseEntity<>(map, HttpStatus.CREATED);
    }

}
