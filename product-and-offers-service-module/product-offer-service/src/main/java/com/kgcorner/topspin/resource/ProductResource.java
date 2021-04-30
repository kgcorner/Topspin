package com.kgcorner.topspin.resource;


import com.kgcorner.topspin.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 29/04/21
 */

@RestController
public class ProductResource {
    @Autowired
    private ProductService productService;
}