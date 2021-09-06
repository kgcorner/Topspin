package com.kgcorner.topspin.service;


import com.kgcorner.topspin.aws.AwsServices;
import com.kgcorner.topspin.aws.exceptions.AwsServiceException;
import com.kgcorner.topspin.dtos.StoreDTO;
import com.kgcorner.topspin.model.AbstractStore;
import com.kgcorner.topspin.persistence.StorePersistenceLayer;
import com.kgcorner.utils.Strings;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 17/04/21
 */

@Service
public class StoreService {

    @Value("${s3.bucket.name}")
    private String s3BucketName;

    @Value("${s3.bucket.url}")
    private String bucketUrl;

    @Autowired
    private StorePersistenceLayer storePersistenceLayer;


    public StoreDTO createStore(StoreDTO storeDTO) {
        AbstractStore store = storePersistenceLayer.createStore(storeDTO);
        BeanUtils.copyProperties(store, storeDTO);
        return storeDTO;
    }

    public StoreDTO updateStore(StoreDTO storeDTO, String storeId) {
        AbstractStore store = storePersistenceLayer.updateStore(storeDTO, storeId);
        BeanUtils.copyProperties(store, storeDTO);
        return storeDTO;
    }

    public StoreDTO getStore(String storeId) {
        AbstractStore abstractStore = storePersistenceLayer.getStore(storeId);
        if(abstractStore == null)
            throw new IllegalArgumentException("No Such store");
        StoreDTO storeDTO = getStoreDTO(abstractStore);
        return storeDTO;
    }

    private StoreDTO getStoreDTO(AbstractStore abstractStore) {
        StoreDTO storeDTO = new StoreDTO();
        BeanUtils.copyProperties(abstractStore, storeDTO);
        return storeDTO;
    }

    public List<StoreDTO> getAllStores(int page, int itemCount) {
        List<AbstractStore> allAbstractStores = storePersistenceLayer.getAllStores(page, itemCount);
        List<StoreDTO> storeDTOS = new ArrayList<>();
        for(AbstractStore abstractStore : allAbstractStores) {
            StoreDTO storeDTO = getStoreDTO(abstractStore);
            storeDTOS.add(storeDTO);
        }
        return storeDTOS;
    }

    public void deleteStore(String storeId) {
        storePersistenceLayer.deleteStore(storeId);
    }

    public StoreDTO updateBannerAndLogo(String storeId, MultipartFile thumbNail, MultipartFile banner, MultipartFile logo) {
        StoreDTO storeDTO = getStore(storeId);
        String bannerName = storeDTO.getName().toLowerCase() + "-banner";
        String logoName = storeDTO.getName().toLowerCase();
        String thumbnailName = storeDTO.getName().toLowerCase() + "-thumb";
        String bannerFileName = banner.getOriginalFilename();
        String thumbnailFileName = thumbNail.getOriginalFilename();
        if(Strings.isNullOrEmpty(bannerFileName)) {
            throw new IllegalArgumentException("Invalid file received");
        }
        bannerName = bannerName + bannerFileName.substring(bannerFileName.lastIndexOf("."));

        String logoFileName = logo.getOriginalFilename();
        if(Strings.isNullOrEmpty(logoFileName)) {
            throw new IllegalArgumentException("Invalid file received");
        }
        logoName = logoName + logoFileName.substring(logoFileName.lastIndexOf("."));

        if(Strings.isNullOrEmpty(thumbnailFileName)) {
            throw new IllegalArgumentException("Invalid file received");
        }
        thumbnailName = thumbnailName + thumbnailFileName.substring(thumbnailFileName.lastIndexOf("."));
        AwsServices awsServices = AwsServices.getInstance();
        try {
            awsServices.storeImage(s3BucketName, bannerName, banner.getInputStream(), banner.getSize());
            bannerName = this.bucketUrl +"/" +bannerName;
            bannerName = bannerName.replace("//","/");
            storeDTO.setBannerImage(bannerName);

            awsServices.storeImage(s3BucketName, logoName, logo.getInputStream(), logo.getSize());
            logoName = this.bucketUrl +"/" +logoName;
            logoName = logoName.replace("//","/");
            storeDTO.setLogo(logoName);
            awsServices.storeImage(s3BucketName, thumbnailName, thumbNail.getInputStream(), thumbNail.getSize());
            thumbnailName = this.bucketUrl +"/" +thumbnailName;
            thumbnailName = thumbnailName.replace("//","/");
            storeDTO.setThumbnailImage(thumbnailName);
            return updateStore(storeDTO, storeId);
        } catch (IOException e) {
            throw new IllegalArgumentException("invalid file received");
        } catch (AwsServiceException e) {
            throw new RuntimeException("Storing file failed");
        }
    }
}