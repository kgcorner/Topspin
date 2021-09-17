package com.kgcorner.topspin.model;

import org.junit.Before;
import org.junit.Test;
import org.springframework.hateoas.Link;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;


/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 17/04/21
 */

public class CategoryDTOTest {

    private CategoryDTO category;

    @Before
    public void setUp() {
        category = new CategoryDTO();
    }

    @Test
    public void getChildren() {
        int maxCount = 10;
        List<CategoryDTO> children = new ArrayList<>();
        for (int i = 0; i < maxCount; i++) {
            children.add(new CategoryDTO());
        }
        category.setChildren(children);
        assertEquals(maxCount, category.getChildren().size());
    }

    @Test
    public void setCategoryId() {
        String categoryId = "categoryId";
        category.setCategoryId(categoryId);
        assertEquals(categoryId, category.getCategoryId());
    }

    @Test
    public void setName() {
        String name = "name";
        category.setName(name);
        assertEquals(name, category.getName());
    }

    @Test
    public void setDescription() {
        String description = "description";
        category.setDescription(description);
        assertEquals(description, category.getDescription());
    }

    @Test
    public void setLongDescription() {
        String longDescription = "longDescription";
        category.setLongDescription(longDescription);
        assertEquals(longDescription, category.getLongDescription());
    }

    @Test
    public void setBannerImage() {
        String bannerImage = "bannerImage";
        category.setBannerImage(bannerImage);
        assertEquals(bannerImage, category.getBannerImage());
    }

    @Test
    public void setThumbnailImage() {
        String thumbnailImage = "thumbnailImage";
        category.setThumbnailImage(thumbnailImage);
        assertEquals(thumbnailImage, category.getThumbnailImage());
    }

    @Test
    public void setLargeImage() {
        String largerImage = "largerImage";
        category.setLargeImage(largerImage);
        assertEquals(largerImage, category.getLargeImage());
    }

    @Test
    public void setParent() {
        CategoryDTO parent = new CategoryDTO();
        category.setParent(parent);
        assertEquals(parent, category.getParent());
    }

    @Test
    public void setTagline() {
        String tagline = "tagline";
        category.setTagline(tagline);
        assertEquals(tagline, category.getTagline());
    }

    @Test
    public void getLinks() {
        String link = "link";
        String rel = "self";
        category.addLink(link, rel);
        List<Link> links = category.getLinks();
        assertEquals(link, links.get(0).getHref());
        assertEquals(rel, links.get(0).getRel());
    }
}