package com.kgcorner.topspin.services;


import com.kgcorner.exceptions.ResourceNotFoundException;
import com.kgcorner.topspin.dto.UserDTO;
import com.kgcorner.topspin.model.AbstractUser;
import com.kgcorner.topspin.persistence.UserPersistenceLayer;
import com.kgcorner.utils.Strings;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 02/12/19
 */

@Service
public class UserService {
    private static final String EMAIL_PATTERN = "^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$";

    @Autowired
    private UserPersistenceLayer userPersistenceLayer;



    
    public UserDTO getUser(String userId) {
        AbstractUser user =  userPersistenceLayer.getUser(userId);
        if(user == null)
            throw new ResourceNotFoundException("No such user exists");
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(user, userDTO);
        return userDTO;
    }

    
    public List<UserDTO> getAllUsers(int page, int maxItems) {
        List<AbstractUser> users = userPersistenceLayer.getUsers(page, maxItems);
        List<UserDTO> userDTOList = new ArrayList<>();
        for(AbstractUser user : users) {
            UserDTO userDTO = new UserDTO();
            BeanUtils.copyProperties(user, userDTO);
            userDTOList.add(userDTO);
        }
        return userDTOList;
    }

    
    public UserDTO createUser(@NotNull UserDTO userDTO) {
        if(!userDTO.getEmail().matches(EMAIL_PATTERN))
            throw new IllegalArgumentException("Invalid email");
        if(Strings.isNullOrEmpty(userDTO.getName()))
            throw new IllegalArgumentException("Name can't be blank");
        if(Strings.isNullOrEmpty(userDTO.getUserName()))
            throw new IllegalArgumentException("Username can't be blank");
        AbstractUser user = userPersistenceLayer.createUser(userDTO);
        BeanUtils.copyProperties(user, userDTO);
        return userDTO;
    }

    
    public UserDTO updateUser(String id, UserDTO userDTO) {
        AbstractUser user = userPersistenceLayer.updateUser(userDTO, id);
        BeanUtils.copyProperties(user, userDTO);
        return userDTO;
    }

    
    public void deleteUser(String userId) {
        userPersistenceLayer.deleteUser(userId);
    }
}