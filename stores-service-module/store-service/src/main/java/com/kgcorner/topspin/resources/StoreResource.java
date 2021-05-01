package com.kgcorner.topspin.resources;


import com.kgcorner.topspin.dtos.StoreDTO;
import com.kgcorner.topspin.service.StoreService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 17/04/21
 */

@RestController
public class StoreResource {

    private static final Logger LOGGER = Logger.getLogger(StoreResource.class);

    @Autowired
    private StoreService storeService;

    @ApiOperation("Creates a Store")
    @PostMapping("/stores")
    public ResponseEntity<StoreDTO> createStore(
        @ApiParam("name of the store")
        @RequestParam("name") String name,
        @ApiParam("description of the store")
        @RequestParam("description") String description,
        @ApiParam("link of the store")
        @RequestParam("link") String link,
        @ApiParam("affiliateId of the store")
        @RequestParam("affiliateId") String affiliateId,
        @ApiParam("surferPlaceHolder of the store")
        @RequestParam("surferPlaceHolder") String surferPlaceHolder,
        @ApiParam("placeholder of the store")
        @RequestParam("placeholder") String placeholder
    ) {
        StoreDTO storeDto =  storeService.createStore(name, description, link, affiliateId, surferPlaceHolder,
            placeholder);

        Link selfLink = ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(StoreResource.class)
            .get(storeDto.getStoreId())).withSelfRel();
        Link updateLink = ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(StoreResource.class)
            .put(storeDto.getStoreId(), "name", "affiliateId", "link", "surferPlaceHolder",
                "placeHolder", "description")).withSelfRel();
        storeDto.add(selfLink);
        storeDto.add(updateLink);
        return ResponseEntity.ok(storeDto);
    }

    @ApiOperation("Update the store")
    @PutMapping("/stores/{storeId}")
    public ResponseEntity<StoreDTO> put(
        @ApiParam("id of the store")
        @PathVariable("storeId") String storeId,
        @ApiParam("name of the store")
        @RequestParam("name") String name,
        @ApiParam("affiliateId of the store")
        @RequestParam("affiliateId") String affiliateId,
        @ApiParam("link of the store")
        @RequestParam("link") String link,
        @ApiParam("surferPlaceHolder of the store")
        @RequestParam("surferPlaceHolder") String surferPlaceHolder,
        @ApiParam("placeholder of the store")
        @RequestParam("placeholder") String placeholder,
        @ApiParam("description of the store")
        @RequestParam("description") String description) {
        StoreDTO storeDto =  storeService.updateStore(storeId, name, description, link, affiliateId, surferPlaceHolder,
            placeholder);
        Link selfLink = ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(StoreResource.class)
            .get(storeDto.getStoreId())).withSelfRel();
        Link updateLink = ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(StoreResource.class)
            .put(storeDto.getStoreId(), "name","affiliateId", "link", "surferPlaceHolder",
                "placeHolder", "description")).withSelfRel();
        storeDto.add(selfLink);
        storeDto.add(updateLink);
        return ResponseEntity.ok(storeDto);
    }

    @ApiOperation("Get the store")
    @GetMapping("/stores/{storeId}")
    public ResponseEntity<StoreDTO> get(
        @ApiParam("id of the store")
        @PathVariable("storeId") String storeId) {
        StoreDTO storeDto =  storeService.getStore(storeId);
        Link selfLink = ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(StoreResource.class)
            .get(storeDto.getStoreId())).withSelfRel();
        Link updateLink = ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(StoreResource.class)
            .put(storeDto.getStoreId(), "name","affiliateId", "link", "surferPlaceHolder",
                "placeHolder", "description")).withSelfRel();
        storeDto.add(selfLink);
        storeDto.add(updateLink);
        return ResponseEntity.ok(storeDto);
    }

    @ApiOperation("Get All Stores")
    @GetMapping("/stores")
    public ResponseEntity<Resources<StoreDTO>> getAllStores(
        @ApiParam("page number")
            @RequestParam(name = "id", defaultValue = "0") int page,
        @ApiParam("Items per page")
            @RequestParam(name = "item-count", defaultValue = "0") int itemPerPage) {
        List<StoreDTO> allStores = storeService.getAllStores(page, itemPerPage);
        Resources<StoreDTO> resources = new Resources<>(allStores);
        String uriString = ServletUriComponentsBuilder.fromCurrentRequest().build().toUriString();
        uriString = uriString.replace("page="+page, "page="+(page+1));
        resources.add(new Link(uriString, "next-page"));
        for(StoreDTO storeDTO : allStores) {
            Link selfLink = ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(StoreResource.class)
                .get(storeDTO.getStoreId())).withSelfRel();
            storeDTO.add(selfLink);
        }
        return ResponseEntity.ok(resources);
    }
}