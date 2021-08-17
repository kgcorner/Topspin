package com.kgcorner.topspin.dto;


import com.kgcorner.hateos.HATEOSResource;
import com.kgcorner.topspin.model.AbstractUser;
import org.springframework.hateoas.Link;

import java.util.ArrayList;
import java.util.List;

/**
 * Description : DTO for Users
 * Author: kumar
 * Created on : 17/08/21
 */

public class UserDTO extends AbstractUser implements HATEOSResource {
    private List<Link> links = new ArrayList<>();

    @Override
    public List<Link> getLinks() {
        return links;
    }
}