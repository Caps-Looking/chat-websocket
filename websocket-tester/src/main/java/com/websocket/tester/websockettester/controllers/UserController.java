package com.websocket.tester.websockettester.controllers;

import com.websocket.tester.websockettester.models.dtos.ControllerResponse;
import com.websocket.tester.websockettester.models.entities.ApplicationUser;
import com.websocket.tester.websockettester.services.ApplicationUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Api(description = "User management API")
@RestController
@RequestMapping("v1/users")
public class UserController {

    @Autowired
    ApplicationUserService applicationUserService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "Create the user", response = ControllerResponse.class)
    @PostMapping
    public ControllerResponse create(@RequestBody ApplicationUser user) {
        return applicationUserService.save(user);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @ApiOperation(value = "Get user by username", response = ControllerResponse.class)
    @GetMapping(path = "/getByUsername/{username}")
    public ControllerResponse getUserByUsername(@PathVariable String username) {
        return applicationUserService.getUserByUsername(username);
    }

}
