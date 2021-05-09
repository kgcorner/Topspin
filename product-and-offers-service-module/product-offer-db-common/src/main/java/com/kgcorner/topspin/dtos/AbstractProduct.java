package com.kgcorner.topspin.dtos;


/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 29/04/21
 */

public abstract class AbstractProduct implements Product{

    private String productSku;

    private String productName;

    private String productDescription;

    private double productPrice;

    private String productPriceCurrency;

    private double wasPrice;

    private double discountedPrice;

    private String productUrl;

    private String pid;

    private String mid;

    private String productSmallImageUrl;

    private String productMediumImageUrl;

    private String productLargeImageUrl;

    private String mpn;

    private String stockAvailability;

    private String brand;

    private String location;

    private String color;

    private String custom1;

    private String custom2;

    private String custom3;

    private String custom4;

    private String custom5;

    private String categoryId;

    private String categoryPathAsString;

    private String storeId;

    private int viewCount;

    private String storeName;

    public String getProductSku() {
        return productSku;
    }

    public String getProductName() {
        return productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public String getProductPriceCurrency() {
        return productPriceCurrency;
    }

    public double getWasPrice() {
        return wasPrice;
    }

    public double getDiscountedPrice() {
        return discountedPrice;
    }

    public String getProductUrl() {
        return productUrl;
    }

    public String getPid() {
        return pid;
    }

    public String getMid() {
        return mid;
    }

    public String getProductSmallImageUrl() {
        return productSmallImageUrl;
    }

    public String getProductMediumImageUrl() {
        return productMediumImageUrl;
    }

    public String getProductLargeImageUrl() {
        return productLargeImageUrl;
    }

    public String getMpn() {
        return mpn;
    }

    public String getStockAvailability() {
        return stockAvailability;
    }

    public String getBrand() {
        return brand;
    }

    public String getLocation() {
        return location;
    }

    public String getColor() {
        return color;
    }

    public String getCustom1() {
        return custom1;
    }

    public String getCustom2() {
        return custom2;
    }

    public String getCustom3() {
        return custom3;
    }

    public String getCustom4() {
        return custom4;
    }

    public String getCustom5() {
        return custom5;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public String getCategoryPathAsString() {
        return categoryPathAsString;
    }

    public String getStoreId() {
        return storeId;
    }

    public int getViewCount() {
        return viewCount;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setProductSku(String productSku) {
        this.productSku = productSku;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public void setProductPriceCurrency(String productPriceCurrency) {
        this.productPriceCurrency = productPriceCurrency;
    }

    public void setWasPrice(double wasPrice) {
        this.wasPrice = wasPrice;
    }

    public void setDiscountedPrice(double discountedPrice) {
        this.discountedPrice = discountedPrice;
    }

    public void setProductUrl(String productUrl) {
        this.productUrl = productUrl;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public void setProductSmallImageUrl(String productSmallImageUrl) {
        this.productSmallImageUrl = productSmallImageUrl;
    }

    public void setProductMediumImageUrl(String productMediumImageUrl) {
        this.productMediumImageUrl = productMediumImageUrl;
    }

    public void setProductLargeImageUrl(String productLargeImageUrl) {
        this.productLargeImageUrl = productLargeImageUrl;
    }

    public void setMpn(String mpn) {
        this.mpn = mpn;
    }

    public void setStockAvailability(String stockAvailability) {
        this.stockAvailability = stockAvailability;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setCustom1(String custom1) {
        this.custom1 = custom1;
    }

    public void setCustom2(String custom2) {
        this.custom2 = custom2;
    }

    public void setCustom3(String custom3) {
        this.custom3 = custom3;
    }

    public void setCustom4(String custom4) {
        this.custom4 = custom4;
    }

    public void setCustom5(String custom5) {
        this.custom5 = custom5;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public void setCategoryPathAsString(String categoryPathAsString) {
        this.categoryPathAsString = categoryPathAsString;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }
}