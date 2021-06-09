package com.kgcorner.topspin.dtos;


import javax.persistence.*;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 29/04/21
 */
@Entity
@Table(name = "PRODUCTS")
public class ProductModel extends AbstractProduct {

    private String productId;

    @Id
    @Column(name="ID")
    @Override
    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    @Column(name = "PRODUCT_SKU")
    @Override
    public String getProductSku() {
        return super.getProductSku();
    }

    @Column(name = "PRODUCT_NAME")
    @Override
    public String getProductName() {
        return super.getProductName();
    }

    @Column(name = "PRODUCT_DESCRIPTION")
    @Override
    public String getProductDescription() {
        return super.getProductDescription();
    }

    @Column(name = "PRODUCT_PRICE")
    @Override
    public double getProductPrice() {
        return super.getProductPrice();
    }

    @Column(name = "PRODUCT_PRICE_CURRENCY")
    @Override
    public String getProductPriceCurrency() {
        return super.getProductPriceCurrency();
    }

    @Column(name = "WAS_PRICE")
    @Override
    public double getWasPrice() {
        return super.getWasPrice();
    }

    @Column(name = "DISCOUNTED_PRICE")
    @Override
    public double getDiscountedPrice() {
        return super.getDiscountedPrice();
    }

    @Column(name = "PRODUCT_URL")
    @Override
    public String getProductUrl() {
        return super.getProductUrl();
    }

    @Column(name = "PID")
    @Override
    public String getPid() {
        return super.getPid();
    }

    @Column(name = "MID")
    @Override
    public String getMid() {
        return super.getMid();
    }

    @Column(name = "PRODUCT_IMAGE_SMALL_URL")
    @Override
    public String getProductSmallImageUrl() {
        return super.getProductSmallImageUrl();
    }

    @Column(name = "PRODUCT_IMAGE_MEDIUM_URL")
    @Override
    public String getProductMediumImageUrl() {
        return super.getProductMediumImageUrl();
    }

    @Column(name = "PRODUCT_IMAGE_LARGE_URL")
    @Override
    public String getProductLargeImageUrl() {
        return super.getProductLargeImageUrl();
    }

    @Column(name = "MPN")
    @Override
    public String getMpn() {
        return super.getMpn();
    }

    @Column(name = "STOCK_AVAILABILITY")
    @Override
    public String getStockAvailability() {
        return super.getStockAvailability();
    }

    @Column(name = "BRAND")
    @Override
    public String getBrand() {
        return super.getBrand();
    }

    @Column(name = "LOCATION")
    @Override
    public String getLocation() {
        return super.getLocation();
    }

    @Column(name = "COLOR")
    @Override
    public String getColor() {
        return super.getColor();
    }

    @Column(name = "CUSTOM1")
    @Override
    public String getCustom1() {
        return super.getCustom1();
    }

    @Column(name = "CUSTOM2")
    @Override
    public String getCustom2() {
        return super.getCustom2();
    }

    @Column(name = "CUSTOM3")
    @Override
    public String getCustom3() {
        return super.getCustom3();
    }

    @Column(name = "CUSTOM4")
    @Override
    public String getCustom4() {
        return super.getCustom4();
    }

    @Column(name = "CUSTOM5")
    @Override
    public String getCustom5() {
        return super.getCustom5();
    }

    @ManyToOne
    @JoinColumn(name ="CATEGORY_ID", nullable = false)
    @Override
    public CategoryReferenceModel getCategory() {
        return (CategoryReferenceModel)super.getCategory();
    }

    @Column(name = "CATEGORY_PATH_AS_STRING")
    @Override
    public String getCategoryPathAsString() {
        return super.getCategoryPathAsString();
    }

    @ManyToOne
    @JoinColumn(name ="STORE_ID", nullable = false)
    @Override
    public StoreReferenceModel getStore() {
        return (StoreReferenceModel) super.getStore();
    }

    @Column(name = "VIEW_COUNT")
    @Override
    public int getViewCount() {
        return super.getViewCount();
    }
}