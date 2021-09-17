package com.kgcorner.topspin.services;


import com.kgcorner.exceptions.ResourceNotFoundException;
import com.kgcorner.topspin.aws.AwsServices;
import com.kgcorner.topspin.aws.exceptions.AwsServiceException;
import com.kgcorner.topspin.client.CategoryClient;
import com.kgcorner.topspin.client.StoreClient;
import com.kgcorner.topspin.dtos.OfferDTO;
import com.kgcorner.topspin.model.*;
import com.kgcorner.topspin.persistence.OfferPersistenceLayer;
import com.kgcorner.topspin.persistence.ProductOfferCategoryPersistenceLayer;
import com.kgcorner.topspin.persistence.ProductOfferStorePersistenceLayer;
import com.kgcorner.utils.Strings;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 13/09/21
 */

@Service
public class OfferService {

    @Autowired
    private OfferPersistenceLayer offerPersistenceLayer;

    @Autowired
    private ProductOfferCategoryPersistenceLayer categoryPersistenceLayer;

    @Autowired
    private ProductOfferStorePersistenceLayer storePersistenceLayer;

    @Autowired
    private StoreClient storeClient;

    @Autowired
    private CategoryClient categoryClient;

    @Value("${s3.bucket.name}")
    private String s3BucketName;

    @Value("${s3.bucket.url}")
    private String bucketUrl;

    public OfferDTO createOffer(OfferDTO offerDTO) {
        CategoryRef category = categoryPersistenceLayer.getCategory(offerDTO.getCategory().getId());
        StoreRef store = storePersistenceLayer.getStore(offerDTO.getStore().getId());

        if(category == null) {
            CategoryDTO categoryDTO = categoryClient.getCategory(offerDTO.getCategory().getId());
            category = new CategoryRef();
            category.setDescription(categoryDTO.getDescription());
            category.setId(categoryDTO.getCategoryId());
            category.setName(categoryDTO.getName());
            category = categoryPersistenceLayer.createCategory(category);
            offerDTO.setCategory(category);
        }

        if(store == null) {
            StoreDTO storeDTO = storeClient.get(offerDTO.getStore().getId());
            store = new StoreRef();
            store.setDescription(storeDTO.getDescription());
            store.setId(storeDTO.getStoreId());
            store.setName(storeDTO.getName());
            store = storePersistenceLayer.createStore(store);
            offerDTO.setStore(store);
        }
        offerDTO.setCategory(category);
        offerDTO.setStore(store);
        AbstractOffer offer = offerPersistenceLayer.createOffer(offerDTO);
        BeanUtils.copyProperties(offer, offerDTO);
        return offerDTO;
    }

    public OfferDTO updateOffer(OfferDTO offerDTO) {
        AbstractOffer offer = offerPersistenceLayer.updateOffer(offerDTO);
        BeanUtils.copyProperties(offer, offerDTO);
        return offerDTO;
    }

    public List<OfferDTO> getAllOffers(int page, int maxCount, boolean onlyFeatured, String storeId, String categoryId) {
        CategoryRef category = null;
        StoreRef store = null;
        if(!Strings.isNullOrEmpty(storeId)) {
            store = storePersistenceLayer.getStore(storeId);
        }
        if(!Strings.isNullOrEmpty(categoryId)) {
            category = categoryPersistenceLayer.getCategory(categoryId);
        }
        List<AbstractOffer> allOffers = offerPersistenceLayer.getAll(page, maxCount, onlyFeatured, store, category);
        return getOfferDTOS(allOffers);
    }

    private List<OfferDTO> getOfferDTOS(List<AbstractOffer> allOffers) {
        List<OfferDTO> offerDTOS = new ArrayList<>();
        for(AbstractOffer offer : allOffers) {
            OfferDTO offerDTO = new OfferDTO();
            BeanUtils.copyProperties(offerDTO, offer);
            offerDTOS.add(offerDTO);
        }
        return offerDTOS;
    }

    public void deleteOffer(String offerId) {
        if(Strings.isNullOrEmpty(offerId)) {
            throw new IllegalArgumentException("Offer Id can't be null");
        }
        getOffer(offerId); // to check whether offer exists or not
        offerPersistenceLayer.deleteOffer(offerId);
    }

    public OfferDTO getOffer(String offerId) {
        Assert.notNull(offerId, "Offer Id can't be null");
        AbstractOffer offer = offerPersistenceLayer.getOffer(offerId);
        if(offer == null)
            throw new ResourceNotFoundException("No such offer exists");
        OfferDTO offerDTO = new OfferDTO();
        BeanUtils.copyProperties(offer, offerDTO);
        return offerDTO;
    }

    public List<OfferDTO> getOffersOfCategory(String categoryId, int page, int count) {
        if(Strings.isNullOrEmpty(categoryId)) {
            throw new IllegalArgumentException("Category Id can't be null");
        }

        CategoryRef category = categoryPersistenceLayer.getCategory(categoryId);
        if(category == null)
            throw new ResourceNotFoundException("No such category exists");
        List<AbstractOffer> allOfferFromCategory = offerPersistenceLayer.getAllOfferFromCategory(category, page, count);
        return getOfferDTOS(allOfferFromCategory);
    }

    public List<OfferDTO> getOffersOfStores(String storeId, int page, int count) {
        if(Strings.isNullOrEmpty(storeId)) {
            throw new IllegalArgumentException("Store Id can't be null");
        }

        StoreRef store = storePersistenceLayer.getStore(storeId);
        if(store == null)
            throw new ResourceNotFoundException("No such store exists");
        List<AbstractOffer> allOfferFromCategory = offerPersistenceLayer.getAllOfferFromStore(store, page, count);
        return getOfferDTOS(allOfferFromCategory);
    }


    public OfferDTO uploadImage(String offerId, MultipartFile image) throws IOException {
        AbstractOffer offer = offerPersistenceLayer.getOffer(offerId);
        AwsServices awsServices = AwsServices.getInstance();
        try {
            awsServices.storeImage(s3BucketName, image.getOriginalFilename(),image.getInputStream(), image.getSize());
            String url = bucketUrl + image.getOriginalFilename();
            String thumbnails = offer.getThumbnails();
            if(!Strings.isNullOrEmpty(thumbnails)) {
                url = thumbnails + "," + url;
            }
            thumbnails = url;
            offer.setThumbnails(thumbnails);
            offer = offerPersistenceLayer.updateOffer(offer);
            OfferDTO offerDTO = new OfferDTO();
            BeanUtils.copyProperties(offer, offerDTO);
            return offerDTO;
        } catch (AwsServiceException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }
}