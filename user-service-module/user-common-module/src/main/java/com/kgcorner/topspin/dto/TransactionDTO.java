package com.kgcorner.topspin.dto;


import com.kgcorner.hateos.HATEOSResource;
import com.kgcorner.topspin.model.AbstractTransaction;
import org.springframework.hateoas.Link;

import java.util.ArrayList;
import java.util.List;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 18/08/21
 */

public class TransactionDTO extends AbstractTransaction implements HATEOSResource {
    private List<Link> links = new ArrayList<>();

    @Override
    public List<Link> getLinks() {
        return links;
    }
}