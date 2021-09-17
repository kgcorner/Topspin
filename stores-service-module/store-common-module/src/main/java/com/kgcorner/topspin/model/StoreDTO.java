package com.kgcorner.topspin.model;


import com.kgcorner.hateos.HATEOSResource;
import org.springframework.hateoas.Link;

import java.util.ArrayList;
import java.util.List;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 15/08/21
 */

public class StoreDTO extends AbstractStore<CategoryDTO> implements HATEOSResource {
    private List<Link> links = new ArrayList<>();

    @Override
    public List<Link> getLinks() {
        return links;
    }
}