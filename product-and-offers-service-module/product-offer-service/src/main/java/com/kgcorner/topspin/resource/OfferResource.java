package com.kgcorner.topspin.resource;


import com.kgcorner.topspin.dtos.OfferDTO;
import com.kgcorner.topspin.service.OfferService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 29/04/21
 */

@RestController
public class OfferResource {

    @Autowired
    private OfferService offerService;

    @ApiOperation("Creates an Offer")
    @PostMapping("/offers")
    public ResponseEntity<OfferDTO> createOffer(
        @ApiParam("title of the offer")
        @RequestParam("title") String title,
        @ApiParam("title of the offer")
        @RequestParam("description") String description,
        @ApiParam("title of the offer")
        @RequestParam("lastDate") Date lastDate,
        @ApiParam("title of the offer")
        @RequestParam("categoryId") String categoryId,
        @ApiParam("title of the offer")
        @RequestParam("storeId") String storeId,
        @ApiParam("title of the offer")
        @RequestParam("url") String url,
        @ApiParam("title of the offer")
        @RequestParam("maxDiscount") String maxDiscount,
        @ApiParam("title of the offer")
        @RequestParam("thumbnails") String thumbnails,
        @ApiParam("title of the offer")
        @RequestParam("featured") boolean featured
    ) {
        OfferDTO offer =   offerService.createOffer(title, description, lastDate, categoryId, storeId, url,
            maxDiscount, thumbnails, featured);
        Link selfRel = ControllerLinkBuilder.linkTo(ControllerLinkBuilder
            .methodOn(OfferResource.class).get(offer.getOfferId())).withSelfRel();
        offer.add(selfRel);
        return ResponseEntity.ok(offer);
    }

    @ApiOperation("Get offer by an ID")
    @GetMapping("/offers/{offerId}")
    public ResponseEntity<OfferDTO> get(@PathVariable("offerId") String offerId) {
        OfferDTO offer = offerService.getOffer(offerId);
        Link selfRel = ControllerLinkBuilder.linkTo(ControllerLinkBuilder
            .methodOn(OfferResource.class).get(offer.getOfferId())).withSelfRel();
        offer.add(selfRel);
        return ResponseEntity.ok(offer);
    }

}