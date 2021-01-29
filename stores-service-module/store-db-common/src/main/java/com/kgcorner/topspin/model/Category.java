package com.kgcorner.topspin.model;


import java.io.Serializable;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 27/01/21
 */

public interface Category extends Serializable {
    /**
     * Returns name of the category
     */
    String getName();


    /**
     * @return short description about the category
     */
    String getDescription();

    /**
     * Use only when a long description for a category is required. Used for filling client's page with content
     * It written well, it could boost on-page SEO for this category. It is expected to have html output
     * @return long description of the category
     */
    String getLongDescription();

    /**
     * @return returns path of the banner image for the category
     */
    String getBannerImage();

    /**
     * @return the path of thumbnail image of the category
     */
    String getThumbNailImage();

    /**
     * @return large image of the category. should be in square or similar size
     */
    String getLargeImage();

    /**
     * @return parent category
     */
    Category getParent();

    /**
     * @return returns tag line for the category, if there's any
     */
    String getTagLine();

    /**
     * Get Category's Id
     * @return
     */
    String getCategoryId();

}