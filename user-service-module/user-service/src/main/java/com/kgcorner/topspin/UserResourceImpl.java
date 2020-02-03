package com.kgcorner.topspin;


import com.kgcorner.topspin.model.User;
import com.kgcorner.topspin.services.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Description : Implementation of {@link UserResource}
 * Author: kumar
 * Created on : 02/12/19
 */

public class UserResourceImpl implements UserResource {

    @Autowired
    private UserService userService;

    @ApiOperation("Returns user with given id")
    @GetMapping("/users/{user-id")
    @Override
    public User getUser( @ApiParam(value = "Id of the user", required = true)
         @PathVariable("user-id") String userId) {
        return userService.getUser(userId);
    }

    @ApiOperation("Returns list of users page wise")
    @GetMapping("/users?page={page-num}")
    @Override
    public List<User> getAllUsers(@ApiParam(value = "Page number", required = true)
                                      @RequestParam("page-num") int page) {
        return userService.getAllUsers(page);
    }
}