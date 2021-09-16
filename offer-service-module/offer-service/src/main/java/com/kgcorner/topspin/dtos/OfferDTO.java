package com.kgcorner.topspin.dtos;


import com.kgcorner.hateos.HATEOSResource;
import com.kgcorner.topspin.model.AbstractOffer;
import org.springframework.hateoas.Link;

import java.util.ArrayList;
import java.util.List;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 13/09/21
 */

public class OfferDTO extends AbstractOffer implements HATEOSResource {

    private List<Link> links = new ArrayList<>();

    @Override
    public List<Link> getLinks() {
        return links;
    }
}