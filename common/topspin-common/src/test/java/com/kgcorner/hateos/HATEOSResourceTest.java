package com.kgcorner.hateos;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.hateoas.Link;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;


/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 08/09/21
 */

public class HATEOSResourceTest {

    @Test
    public void addLink() {
        HATEOSResource resource = new HATEOSResource() {
            private List<Link> links = new ArrayList<>();
            @Override
            public List<Link> getLinks() {
                return links;
            }
        };
        String href = "href";
        String rel = "rel";
        resource.addLink(href, rel);
        List<Link> links = resource.getLinks();
        Assert.assertEquals(1, links.size());
        Assert.assertEquals(href, links.get(0).getHref());
        Assert.assertEquals(rel, links.get(0).getRel());
    }
}