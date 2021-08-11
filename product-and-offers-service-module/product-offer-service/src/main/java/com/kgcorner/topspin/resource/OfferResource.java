package com.kgcorner.topspin.resource;


import com.kgcorner.topspin.aws.AwsServices;
import com.kgcorner.topspin.aws.exceptions.AwsServiceException;
import com.kgcorner.topspin.dtos.OfferDTO;
import com.kgcorner.topspin.service.OfferService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    @Value("${s3.bucket.name}")
    private String s3BucketName;

    @Value("${s3.bucket.url}")
    private String bucketUrl;

    @ApiOperation("Creates an Offer")
    @PostMapping("/offers")
    public ResponseEntity<OfferDTO> createOffer(
        @ApiParam("title of the offer")
        @RequestParam("title") String title,
        @ApiParam("description of the offer")
        @RequestParam("description") String description,
        @ApiParam("last date of the offer")
        @RequestParam("lastDate") Date lastDate,
        @ApiParam("categoryId of the offer")
        @RequestParam("categoryId") String categoryId,
        @ApiParam("storeId of the offer")
        @RequestParam("storeId") String storeId,
        @ApiParam("url of the offer")
        @RequestParam("url") String url,
        @ApiParam("maxDiscount of the offer")
        @RequestParam("maxDiscount") String maxDiscount,
        @ApiParam("thumbnails for the offer")
        @RequestParam("thumbnails") String thumbnails,
        @ApiParam("is this featured offer")
        @RequestParam("featured") boolean featured
    ) {
        OfferDTO offer =   offerService.createOffer(title, description, lastDate, categoryId, storeId, url,
            maxDiscount, thumbnails, featured);
        return getOfferDTOResponseEntity(offer, HttpStatus.CREATED);
    }

    @ApiOperation("Creates an Offer")
    @PostMapping(value = "/offers", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<OfferDTO> createOffer(
        @ApiParam("title of the offer")
        @RequestParam("title") String title,
        @ApiParam("description of the offer")
        @RequestParam("description") String description,
        @ApiParam("last date of the offer")
        @RequestParam("lastDate") Date lastDate,
        @ApiParam("categoryId of the offer")
        @RequestParam("categoryId") String categoryId,
        @ApiParam("storeId of the offer")
        @RequestParam("storeId") String storeId,
        @ApiParam("url of the offer")
        @RequestParam("url") String url,
        @ApiParam("maxDiscount of the offer")
        @RequestParam("maxDiscount") String maxDiscount,
        @ApiParam("thumbnails for the offer")
        @RequestParam("thumbnail")MultipartFile thumbnail,
        @ApiParam("is this featured offer")
        @RequestParam("featured") boolean featured
    ) throws IOException, AwsServiceException {
        AwsServices awsServices = AwsServices.getInstance();
        String fileName = title.length() > 63 ? title.substring(0, 59) : title;
        fileName = awsServices.sanitizeFileName(fileName);
        fileName = fileName + thumbnail.getOriginalFilename().substring(thumbnail.getOriginalFilename().lastIndexOf("."));
        awsServices.storeImage(s3BucketName, fileName, thumbnail.getInputStream(), thumbnail.getSize());
        fileName = this.bucketUrl +"/" +fileName;
        fileName = fileName.replace("//","/");
        OfferDTO offer =   offerService.createOffer(title, description, lastDate, categoryId, storeId, url,
            maxDiscount, fileName, featured);
        return getOfferDTOResponseEntity(offer, HttpStatus.CREATED);
    }

    @ApiOperation("Get offer by an ID")
    @GetMapping("/offers/{offerId}")
    public ResponseEntity<OfferDTO> get(@PathVariable("offerId") String offerId) {
        OfferDTO offer = offerService.getOffer(offerId);
        return getOfferDTOResponseEntity(offer, HttpStatus.OK);
    }

    @ApiOperation("Updates an Offer")
    @PutMapping("/offers/{offerId}")
    public ResponseEntity<OfferDTO> updateOffer(
        @ApiParam("id of the offer")
        @PathVariable("offerId") String offerId,
        @ApiParam("title of the offer")
        @RequestParam("title") String title,
        @ApiParam("description of the offer")
        @RequestParam("description") String description,
        @ApiParam("last date of the offer")
        @RequestParam("lastDate") Date lastDate,
        @ApiParam("categoryId of the offer")
        @RequestParam("categoryId") String categoryId,
        @ApiParam("storeId of the offer")
        @RequestParam("storeId") String storeId,
        @ApiParam("url of the offer")
        @RequestParam("url") String url,
        @ApiParam("maxDiscount of the offer")
        @RequestParam("maxDiscount") String maxDiscount,
        @ApiParam("thumbnails for the offer")
        @RequestParam("thumbnails") String thumbnails,
        @ApiParam("is this featured offer")
        @RequestParam("featured") boolean featured
    ) {
        OfferDTO offer =   offerService.updateOffer(offerId, title, description, lastDate, categoryId, storeId, url,
            maxDiscount, thumbnails, featured);
        return getOfferDTOResponseEntity(offer, HttpStatus.OK);
    }

    @ApiOperation("deletes an offer")
    @DeleteMapping("/offers/{offerId}")
    public ResponseEntity<Object> deleteOffer(
        @ApiParam("Id of the offer")
        @PathVariable("offerId") String offerId
    ) {
        offerService.deleteOffer(offerId);
        return ResponseEntity.noContent().build();
    }

    private ResponseEntity<OfferDTO> getOfferDTOResponseEntity(OfferDTO offer, HttpStatus status) {
        Link selfRel = ControllerLinkBuilder.linkTo(ControllerLinkBuilder
            .methodOn(OfferResource.class).get(offer.getOfferId())).withSelfRel();
        offer.add(selfRel);
        return ResponseEntity.status(status).body(offer);
    }

}