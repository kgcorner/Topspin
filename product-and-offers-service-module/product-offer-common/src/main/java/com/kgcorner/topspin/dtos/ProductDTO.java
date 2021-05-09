package com.kgcorner.topspin.dtos;

import org.springframework.hateoas.ResourceSupport;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 29/04/21
 */

public class ProductDTO extends ResourceSupport implements Product {

    private Product product;
    public ProductDTO(Product product) {
        this.product = product;
    }

    @Override
    public String getProductSku() {
        return product.getProductSku();
    }

    @Override
    public String getProductName() {
        return product.getProductName();
    }

    @Override
    public String getProductDescription() {
        return product.getProductDescription();
    }

    @Override
    public double getProductPrice() {
        return product.getProductPrice();
    }

    @Override
    public String getProductPriceCurrency() {
        return product.getProductPriceCurrency();
    }

    @Override
    public double getWasPrice() {
        return product.getWasPrice();
    }

    @Override
    public double getDiscountedPrice() {
        return product.getDiscountedPrice();
    }

    @Override
    public String getProductUrl() {
        return product.getProductUrl();
    }

    @Override
    public String getPid() {
        return product.getPid();
    }

    @Override
    public String getMid() {
        return product.getMid();
    }

    @Override
    public String getProductSmallImageUrl() {
        return product.getProductSmallImageUrl();
    }

    @Override
    public String getProductMediumImageUrl() {
        return product.getProductMediumImageUrl();
    }

    @Override
    public String getProductLargeImageUrl() {
        return product.getProductLargeImageUrl();
    }

    @Override
    public String getMpn() {
        return product.getMpn();
    }

    @Override
    public String getStockAvailability() {
        return product.getStockAvailability();
    }

    @Override
    public String getBrand() {
        return product.getBrand();
    }

    @Override
    public String getLocation() {
        return product.getLocation();
    }

    @Override
    public String getColor() {
        return product.getColor();
    }

    @Override
    public String getCustom1() {
        return product.getCustom1();
    }

    @Override
    public String getCustom2() {
        return product.getCustom2();
    }

    @Override
    public String getCustom3() {
        return product.getCustom3();
    }

    @Override
    public String getCustom4() {
        return product.getCustom4();
    }

    @Override
    public String getCustom5() {
        return product.getCustom5();
    }

    @Override
    public String getCategoryId() {
        return product.getCategoryId();
    }

    @Override
    public String getCategoryPathAsString() {
        return product.getCategoryPathAsString();
    }

    @Override
    public String getStoreId() {
        return product.getStoreId();
    }

    @Override
    public int getViewCount() {
        return product.getViewCount();
    }

    @Override
    public String getStoreName() {
        return product.getStoreName();
    }

    @Override
    public String getProductId() {
        return product.getProductId();
    }
}