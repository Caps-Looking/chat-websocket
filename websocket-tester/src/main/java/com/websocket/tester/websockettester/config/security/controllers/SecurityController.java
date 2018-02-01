package com.websocket.tester.websockettester.config.security.controllers;

import com.websocket.tester.websockettester.models.entities.ApplicationUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@Api(description = "User login API")
@RestController
@RequestMapping("v1")
public class SecurityController {

    @ApiOperation(value = "Return the token", response = ApplicationUser.class)
    @PostMapping("users/login")
    public String login(@RequestBody ApplicationUser user) {
        ApplicationUser userDTO = new ApplicationUser();
        userDTO.setUsername(user.getUsername());
        userDTO.setPassword(user.getPassword());
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject("http://localhost:8080/login", userDTO, String.class);
    }

}
