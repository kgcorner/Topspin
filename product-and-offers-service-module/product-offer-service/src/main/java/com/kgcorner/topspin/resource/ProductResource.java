package com.kgcorner.topspin.resource;


import com.kgcorner.topspin.dtos.ProductDTO;
import com.kgcorner.topspin.service.ProductService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 29/04/21
 */

@RestController
public class ProductResource {
    @Autowired
    private ProductService productService;

    @ApiOperation("Creates a product")
    @PostMapping("/products")
    public ResponseEntity<ProductDTO> createProduct(
        @ApiParam("Title of the product")
        @RequestParam("title") String title,
        @ApiParam("description of the product")
        @RequestParam("description") String description,
        @ApiParam("price of the product")
        @RequestParam("price") double price,
        @ApiParam("discountedPrice of the product")
        @RequestParam("discountedPrice") double discountedPrice,
        @ApiParam("currency of price of the product")
        @RequestParam("currency") String currency,
        @ApiParam("smallImageUrl of the product")
        @RequestParam("smallImageUrl") String smallImageUrl,
        @ApiParam("mediumImageUrl of the product")
        @RequestParam("mediumImageUrl") String mediumImageUrl,
        @ApiParam("largeImageUrl of the product")
        @RequestParam("largeImageUrl") String largeImageUrl,
        @ApiParam("categoryId of the product")
        @RequestParam("categoryId") String categoryId,
        @ApiParam("storeId of the product")
        @RequestParam("storeId") String storeId,
        @ApiParam("brand of the product")
        @RequestParam("brand") String brand,
        @ApiParam("url of the product")
        @RequestParam("url") String url
    ) {
        ProductDTO product = productService.createProduct(title, description, price, discountedPrice, currency, smallImageUrl,
            mediumImageUrl, largeImageUrl, categoryId, storeId, brand, url);
        Link selfLink = ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(ProductResource.class)
            .getProduct(product.getProductId())).withSelfRel();
        product.add(selfLink);
        return ResponseEntity.ok(product);
    }

    @ApiOperation("Get product by id")
    @GetMapping("/products/{id}")
    public ResponseEntity<ProductDTO> getProduct(
        @ApiParam("Id of the product")
        @PathVariable("id")
        String productId) {
        ProductDTO product = productService.getProduct(productId);
        Link selfLink = ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(ProductResource.class)
            .getProduct(product.getProductId())).withSelfRel();
        product.add(selfLink);
        return ResponseEntity.ok(product);
    }

}