package com.demo.diet.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @RequestMapping(value = "/")
    public String hello() {
        return "Hello world, witam po update ;) kolejny test pipeline git -> jenkins -> tomcat test 4";
    }
}
