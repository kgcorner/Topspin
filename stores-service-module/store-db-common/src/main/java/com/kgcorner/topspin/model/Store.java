package com.kgcorner.topspin.model;


import java.io.Serializable;
import java.util.List;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 30/01/21
 */

public interface Store extends Serializable {

    /**
     * returns id of the store
     */
    String getStoreId();
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
    String getThumbnailImage();

    /**
     * @return large image of the category. should be in square or similar size
     */
    String getLargeImage();

    /**
     * @return returns tag line for the category, if there's any
     */
    String getTagLine();

    /**
     * @return max cashback for this store in % or in required currency
     */
    String getMaxCashback();

    /**
     * @return placeholder url which will have place for deep link
     */
    String getPlaceHolder();

    /**
     * @return placeholder url which will have place for deep link and users etc
     */
    String getSurferPlaceHolder();

    /**
     * @return return url for searching on the store
     */
    String getSearchUrl();

    /**
     * @return categories this store belongs to
     */
    List<Category> getCategories();

    /**
     * @return whether affiliation on the store is active or not
     */
    boolean isAffiliated();

    /**
     * @return whether Store is used for selling products
     */
    boolean isActive();

    /**
     * @return returns if store's link should be handled in special way
     */
    boolean isOpenOut();

    /**
     * @return plain link of the store
     */
    String getLink();

    /**
     * @return affiliate id of the store
     */
    String getAffiliateId();

    /**
     * @return path to logo of the store
     */
    String getLogo();

    String getFixedUrls();

    /**
     * @return returns regex of the product id in store
     */
    String getPidRegex();

    /**
     * @return gender, which store primarily targets
     */
    String getGender();
}