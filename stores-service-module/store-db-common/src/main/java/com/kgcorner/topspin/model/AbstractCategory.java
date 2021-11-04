package com.kgcorner.topspin.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 15/08/21
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public abstract class AbstractCategory {
    private String name;
    private String description;
    private String longDescription;
    private String bannerImage;
    private String thumbnailImage;
    private String largeImage;
    private AbstractCategory parent;
    private String tagline;
    private String categoryId;
    private boolean featured;
    private List<? extends AbstractCategory> children = new ArrayList<>();
}