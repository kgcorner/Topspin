package com.kgcorner.topspin;


import com.kgcorner.topspin.dto.UserDTO;
import com.kgcorner.topspin.services.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Description : Implementation of {@link UserResource}
 * Author: kumar
 * Created on : 02/12/19
 */
@RestController
public class UserResource extends UserServiceExceptionHandler{

    public static final String USERS_USER_ID = "/users/{user-id}";
    @Autowired
    private UserService userService;


    @GetMapping("/health")
    public String getHealth() {
        return "Ok";
    }



    @ApiOperation("Creates user")
    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        userDTO = userService.createUser(userDTO);
        userDTO.addLink(USERS_USER_ID.replace("{user-id}", userDTO.getId()), Link.REL_SELF);
        return ResponseEntity.status(HttpStatus.CREATED).body(userDTO);
    }

    @ApiOperation("Updates user, only given values are set")
    @PutMapping(USERS_USER_ID)
    public ResponseEntity<UserDTO> updateUser(
        @ApiParam("id of the user") @PathVariable("user-id") String id,
        @RequestBody UserDTO userDTO
    ) {
        userDTO = userService.updateUser(id, userDTO);
        userDTO.addLink(USERS_USER_ID.replace("{user-id}", userDTO.getId()), Link.REL_SELF);
        return ResponseEntity.ok(userDTO);
    }

    @ApiOperation("Returns user with given id")
    @GetMapping(USERS_USER_ID)
    public ResponseEntity<UserDTO> getUser( @ApiParam(value = "Id of the user", required = true)
                         @PathVariable("user-id") String userId) {
        UserDTO userDTO = userService.getUser(userId);
        userDTO.addLink(USERS_USER_ID.replace("{user-id}", userDTO.getId()), Link.REL_SELF);
        return ResponseEntity.status(HttpStatus.OK).body(userDTO);
    }

    @ApiOperation("Returns user with given username")
    @GetMapping("/users/username/{username}")
    public ResponseEntity<UserDTO> getUserByUsername( @ApiParam(value = "Id of the user", required = true)
                                            @PathVariable("username") String username) {
        UserDTO userDTO = userService.getUserByUsername(username);
        userDTO.addLink(USERS_USER_ID.replace("{user-id}", userDTO.getId()), Link.REL_SELF);
        return ResponseEntity.status(HttpStatus.OK).body(userDTO);
    }

    @ApiOperation("Returns list of users page wise")
    @GetMapping("/users")
    public ResponseEntity<Resources<UserDTO>> getAllUsers(@ApiParam(value = "Page number", required = true)
                                  @RequestParam("page") int page,
                                  @ApiParam(value = "users per page, default 30", required = false, defaultValue = "30")
                                  @RequestParam(value = "item-count", required = false, defaultValue = "30") int maxPerPage) {
        List<UserDTO> allUsers = userService.getAllUsers(page, maxPerPage);
        Resources<UserDTO> resources = new Resources<>(allUsers);
        String uriString = "/users?page=" + page + "&item-count=" + maxPerPage;
        uriString = uriString.replace("page="+page, "page="+(page+1));
        resources.add(new Link(uriString, "next-page"));
        for(UserDTO userDTO : allUsers) {
            userDTO.addLink(USERS_USER_ID.replace("{userId}", userDTO.getId()), Link.REL_SELF);
        }
        return ResponseEntity.status(HttpStatus.OK).body(resources);
    }

    @ApiOperation("Deletes the given user")
    @DeleteMapping(USERS_USER_ID)
    public ResponseEntity<Void> deleteUser( @ApiParam(value = "Id of the user", required = true)
                                                @PathVariable("user-id") String userId) {
        userService.deleteUser(userId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}