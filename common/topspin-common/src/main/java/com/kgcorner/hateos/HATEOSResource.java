package com.kgcorner.hateos;

import org.springframework.hateoas.Link;

import java.util.List;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 15/08/21
 */

public interface HATEOSResource {
    /**
     * Adds a link for resource
     * @param href
     * @param rel
     */
    default void addLink(String href, String rel) {
        Link link = new Link(href, rel);
        getLinks().add(link);
    }

    /**
     * returns links for the resource
     * @return
     */
    List<Link> getLinks();
}