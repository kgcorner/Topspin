package com.kgcorner.topspin.dtos;


import java.io.Serializable;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 29/04/21
 */

public interface Product extends Serializable {

    
    String getProductId();

    String getProductSku();

    String getProductName();

    String getProductDescription();

    double getProductPrice();

    String getProductPriceCurrency();

    double getWasPrice();

    double getDiscountedPrice();

    String getProductUrl();

    String getPid();

    String getMid();

    String getProductSmallImageUrl();

    String getProductMediumImageUrl();

    String getProductLargeImageUrl();

    String getMpn();

    String getStockAvailability();

    String getBrand();

    String getLocation();

    String getColor();

    String getCustom1();

    String getCustom2();

    String getCustom3();

    String getCustom4();

    String getCustom5();

    String getCategoryPathAsString();

    String getStoreId();

    String getCategoryId();

    int getViewCount();

    String getStoreName();
}