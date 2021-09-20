package com.kgcorner.topspin.resources;


import com.kgcorner.topspin.dtos.OfferDTO;
import com.kgcorner.topspin.services.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 13/09/21
 */

@RestController
public class OfferResource {

    private static final String OFFERS_OFFER_ID = "/offers/{offerId}";
    private static final String MANAGE_OFFERS_OFFER_ID = "/manage/offers/{offerId}";

    @Autowired
    private OfferService offerService;

    @GetMapping("/offers")
    public ResponseEntity<Resources<OfferDTO>> getOffers(@RequestParam("page") int page
        , @RequestParam("item-count") int count,
        @RequestParam(value="featured", required = false, defaultValue = "false") boolean onlyFeatured,
        @RequestParam(value="store", required = false, defaultValue = "") String storeId,
        @RequestParam(value="category", required = false, defaultValue = "") String categoryId) {
        List<OfferDTO> offerDTOS = offerService.getAllOffers(page, count, onlyFeatured, storeId, categoryId);
        return getResourcesResponseEntity(page, count, offerDTOS);
    }

    @GetMapping(OFFERS_OFFER_ID)
    public ResponseEntity<OfferDTO> getOffer(@PathVariable("offerId") String offerId) {
        OfferDTO offer = offerService.getOffer(offerId);
        offer.addLink(OFFERS_OFFER_ID.replace("{offerId}", offer.getOfferId()), Link.REL_SELF);
        return ResponseEntity.ok(offer);
    }

    @PostMapping("/manage/offers")
    public ResponseEntity<OfferDTO> createOffer(@RequestBody OfferDTO offerDTO) {
        offerDTO = offerService.createOffer(offerDTO);
        offerDTO.addLink(OFFERS_OFFER_ID.replace("{offerId}", offerDTO.getOfferId()), Link.REL_SELF);
        return ResponseEntity.ok(offerDTO);
    }

    @PutMapping(MANAGE_OFFERS_OFFER_ID)
    public ResponseEntity<OfferDTO> updateOffer(@PathVariable("offerId") String offerId,
                                                @RequestBody OfferDTO offerDTO) {
        if(!offerId.equals(offerDTO.getOfferId())) {
            throw new IllegalArgumentException("Offer id is not matching with provided offer");
        }
        offerDTO = offerService.updateOffer(offerDTO);
        offerDTO.addLink(OFFERS_OFFER_ID.replace("{offerId}", offerDTO.getOfferId()), Link.REL_SELF);
        return ResponseEntity.ok(offerDTO);
    }

    @DeleteMapping(MANAGE_OFFERS_OFFER_ID)
    public ResponseEntity<Void> deleteOffer(@PathVariable("offerId") String offerId) {
        offerService.deleteOffer(offerId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping(MANAGE_OFFERS_OFFER_ID)
    public ResponseEntity<OfferDTO> addThumbnails(@PathVariable("offerId") String offerId,
                                              @RequestParam("image")MultipartFile image) {
        OfferDTO offerDTO = null;
        try {
            offerDTO = offerService.uploadImage(offerId, image);
        } catch (IOException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
        offerDTO.addLink(OFFERS_OFFER_ID.replace("{offerId}", offerDTO.getOfferId()), Link.REL_SELF);
        return ResponseEntity.ok(offerDTO);
    }

    @GetMapping("/offers/store/{storeId}")
    public ResponseEntity<Resources<OfferDTO>> getOffersFromStore(@PathVariable("storeId") String storeId,
                                                                  @RequestParam("page") int page
        , @RequestParam("item-count") int count) {
        List<OfferDTO> offersOfStores = offerService.getOffersOfStores(storeId, page, count);
        return getResourcesResponseEntity(page, count, offersOfStores);
    }

    @GetMapping("/offers/category/{categoryId}")
    public ResponseEntity<Resources<OfferDTO>> getOffersFromCategory(@PathVariable("categoryId") String categoryId,
                                                                  @RequestParam("page") int page
        , @RequestParam("item-count") int count) {
        List<OfferDTO> offersOfCategory = offerService.getOffersOfCategory(categoryId, page, count);
        return getResourcesResponseEntity(page, count, offersOfCategory);
    }

    @GetMapping("/offers/banners")
    public ResponseEntity<Resources<OfferDTO>> getBanners() {
        List<OfferDTO> banners = offerService.getBanners();
        return getResourcesResponseEntity(0, 0, banners);
    }

    private ResponseEntity<Resources<OfferDTO>> getResourcesResponseEntity(int page, int count,
                                                                           List<OfferDTO> offerDTOS) {
        Resources<OfferDTO> offerDTOResources = new Resources<>(offerDTOS);
        String uriString = null;
        if(page > 0 && count > 0) {
            uriString = "/offers?page=" + page + "&item-count=" + count;
            uriString = uriString.replace("page=" + page, "page=" + (page + 1));
            offerDTOResources.add(new Link(uriString, "next-page"));
        }
        for(OfferDTO offerDTO : offerDTOS) {
            offerDTO.addLink(OFFERS_OFFER_ID.replace("{offerId}", offerDTO.getOfferId()), Link.REL_SELF);
        }
        return ResponseEntity.ok(offerDTOResources);
    }
}