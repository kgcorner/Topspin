package com.kgcorner.topspin.dtos;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 29/04/21
 */

public interface Offer extends Serializable {

    String getOfferId();

    String getTitle();

    String getDescription();

    List<String> getThumbnails();

    String getUrl();

    String getSurferPlaceholderUrl();

    Category getCategory();

    Store getStore();

    Date getLastDate();

    String getMaxDiscount();

    boolean isFeatured();
}