package com.kgcorner.topspin.resources;


import com.kgcorner.topspin.model.StoreDTO;
import com.kgcorner.topspin.service.StoreService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 17/04/21
 */

@RestController
public class StoreResource {

    private static final Logger LOGGER = Logger.getLogger(StoreResource.class);
    public static final String STORES_STORE_ID = "/stores/{storeId}";
    public static final String MANAGE_STORES_STORE_ID = "/manage/stores/{storeId}";

    @Autowired
    private StoreService storeService;

    @ApiOperation("Creates a Store")
    @PostMapping("/manage/stores")
    public ResponseEntity<StoreDTO> createStore(
        @RequestBody
        StoreDTO storeDTO
    ) {
        storeDTO =  storeService.createStore(storeDTO);
        storeDTO.addLink(STORES_STORE_ID.replace("{storeId}", storeDTO.getStoreId()), Link.REL_SELF);
        return ResponseEntity.status(HttpStatus.CREATED).body(storeDTO);
    }

    @ApiOperation("Update the store")
    @PutMapping(MANAGE_STORES_STORE_ID)
    public ResponseEntity<StoreDTO> updateStore(
        @ApiParam("id of the store")
        @PathVariable("storeId") String storeId,
        @RequestBody StoreDTO storeDTO) {
        storeDTO =  storeService.updateStore(storeDTO, storeId);
        storeDTO.addLink(STORES_STORE_ID.replace("{storeId}", storeDTO.getStoreId()), Link.REL_SELF);
        return ResponseEntity.ok(storeDTO);
    }

    @ApiOperation("Update the store")
    @PatchMapping(MANAGE_STORES_STORE_ID)
    public ResponseEntity<StoreDTO> uploadBannerAndLogo(
        @ApiParam("id of the store")
        @PathVariable("storeId") String storeId,
        @ApiParam(value = "Banner for the Store")
        @RequestParam("banner") MultipartFile banner,
        @ApiParam(value = "logo for the Store")
        @RequestParam("logo") MultipartFile logo,
        @ApiParam(value = "thumbnail for the Store")
        @RequestParam("thumbnail") MultipartFile thumbnail) {
        StoreDTO storeDTO =  storeService.updateBannerAndLogo(storeId, thumbnail, banner, logo);
        storeDTO.addLink(STORES_STORE_ID.replace("{storeId}", storeDTO.getStoreId()), Link.REL_SELF);
        return ResponseEntity.ok(storeDTO);
    }


    @ApiOperation("Get the store")
    @GetMapping(STORES_STORE_ID)
    public ResponseEntity<StoreDTO> getStore(
        @ApiParam("id of the store")
        @PathVariable("storeId") String storeId) {
        StoreDTO storeDto =  storeService.getStore(storeId);
        storeDto.addLink(STORES_STORE_ID.replace("{storeId}", storeDto.getStoreId()), Link.REL_SELF);
        return ResponseEntity.ok(storeDto);
    }

    @ApiOperation("Get All Stores")
    @GetMapping("/stores")
    public ResponseEntity<Resources<StoreDTO>> getAllStores(
        @ApiParam("page number")
        @RequestParam(name = "page", defaultValue = "0") int page,
        @ApiParam("Items per page")
        @RequestParam(name = "item-count", defaultValue = "0") int itemPerPage) {
        List<StoreDTO> allStores = storeService.getAllStores(page, itemPerPage);
        Resources<StoreDTO> resources = new Resources<>(allStores);
        String uriString = "/stores?page="+page+"&item-count="+itemPerPage;
        uriString = uriString.replace("page="+page, "page="+(page+1));
        resources.add(new Link(uriString, "next-page"));
        for(StoreDTO storeDTO : allStores) {
            storeDTO.addLink(STORES_STORE_ID.replace("{storeId}", storeDTO.getStoreId()), Link.REL_SELF);
        }
        return ResponseEntity.ok(resources);
    }

    @ApiOperation("Delete Store")
    @DeleteMapping(MANAGE_STORES_STORE_ID)
    public ResponseEntity<Void> deleteStore(@ApiParam("Id of the store") @PathVariable("storeId") String storeId) {
        storeService.deleteStore(storeId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}