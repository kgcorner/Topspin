package com.kgcorner.topspin.service;


import com.kgcorner.exceptions.ResourceNotFoundException;
import com.kgcorner.topspin.aws.AwsServices;
import com.kgcorner.topspin.aws.exceptions.AwsServiceException;
import com.kgcorner.topspin.model.CategoryDTO;
import com.kgcorner.topspin.model.AbstractCategory;
import com.kgcorner.topspin.persistence.CategoryPersistenceLayer;
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
 * Created on : 28/01/21
 */

@Service
public class CategoryService {

    private static final String INVALID_FILE_RECEIVED = "Invalid file received";

    @Value("${s3.bucket.name}")
    private String s3BucketName;

    @Value("${s3.bucket.url}")
    private String bucketUrl;

    @Autowired
    private CategoryPersistenceLayer categoryPersistenceLayer;

    public CategoryDTO getCategory(String categoryId) {
        AbstractCategory category = categoryPersistenceLayer.getCategory(categoryId);
        if(category == null)
            throw new IllegalArgumentException("No Such category");
        CategoryDTO dto = new CategoryDTO();
        BeanUtils.copyProperties(category, dto);
        return dto;
    }

    public CategoryDTO createCategory(CategoryDTO category) {
        AbstractCategory abstractCategory = categoryPersistenceLayer.createCategory(category);
        BeanUtils.copyProperties(abstractCategory, category);
        return category;
    }

    public CategoryDTO updateCategory(CategoryDTO category, String categoryId) {
        AbstractCategory categoryInDb = categoryPersistenceLayer.getCategory(categoryId);
        if(categoryInDb == null)
            throw new ResourceNotFoundException("No Such category");
        BeanUtils.copyProperties(category, categoryInDb);
        AbstractCategory abstractCategory = categoryPersistenceLayer.updateCategory(categoryInDb, categoryId);
        BeanUtils.copyProperties(abstractCategory, category);
        return category;
    }

    public List<CategoryDTO> getAllCategories(int page, int perPage) {
        List<? extends AbstractCategory> allCategories = categoryPersistenceLayer.getAllCategories(page, perPage);
        List<CategoryDTO> categoryDTOS = new ArrayList<>();
        for(AbstractCategory category : allCategories) {
            CategoryDTO dto = new CategoryDTO();
            BeanUtils.copyProperties(category, dto);
            categoryDTOS.add(dto);
        }
        return categoryDTOS;
    }

    public void deleteCategory(String categoryId) {
        categoryPersistenceLayer.deleteCategory(categoryId);
    }

    public CategoryDTO updateBannerAndLogo(String categoryId, MultipartFile thumbnail,
                                        MultipartFile banner, MultipartFile largeFile) {
        CategoryDTO categoryDTO = getCategory(categoryId);
        String bannerName = categoryDTO.getName().toLowerCase() + "-banner";
        String logoName = categoryDTO.getName().toLowerCase() + "-large";
        String thumbnailName = categoryDTO.getName().toLowerCase();
        String bannerFileName = banner.getOriginalFilename();
        String thumbnailFileName = thumbnail.getOriginalFilename();
        if(Strings.isNullOrEmpty(bannerFileName)) {
            throw new IllegalArgumentException(INVALID_FILE_RECEIVED);
        }
        bannerName = bannerName + bannerFileName.substring(bannerFileName.lastIndexOf("."));

        String logoFileName = largeFile.getOriginalFilename();
        if(Strings.isNullOrEmpty(logoFileName)) {
            throw new IllegalArgumentException(INVALID_FILE_RECEIVED);
        }
        logoName = logoName + logoFileName.substring(logoFileName.lastIndexOf("."));

        if(Strings.isNullOrEmpty(thumbnailFileName)) {
            throw new IllegalArgumentException(INVALID_FILE_RECEIVED);
        }
        thumbnailName = thumbnailName + thumbnailFileName.substring(thumbnailFileName.lastIndexOf("."));
        AwsServices awsServices = AwsServices.getInstance();
        try {
            awsServices.storeImage(s3BucketName, bannerName, banner.getInputStream(), banner.getSize());
            bannerName = this.bucketUrl +"/" +bannerName;
            bannerName = bannerName.replace("//","/");
            categoryDTO.setBannerImage(bannerName);

            awsServices.storeImage(s3BucketName, logoName, largeFile.getInputStream(), largeFile.getSize());
            logoName = this.bucketUrl +"/" +logoName;
            logoName = logoName.replace("//","/");
            categoryDTO.setLargeImage(logoName);
            awsServices.storeImage(s3BucketName, thumbnailName, thumbnail.getInputStream(), thumbnail.getSize());
            thumbnailName = this.bucketUrl +"/" +thumbnailName;
            thumbnailName = thumbnailName.replace("//","/");
            categoryDTO.setThumbnailImage(thumbnailName);
            return updateCategory(categoryDTO, categoryId);
        } catch (IOException e) {
            throw new IllegalArgumentException(INVALID_FILE_RECEIVED);
        } catch (AwsServiceException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public CategoryDTO addChildren(String categoryId, List<CategoryDTO> children) {
        AbstractCategory category = categoryPersistenceLayer.getCategory(categoryId);
        if(category == null) {
            throw new ResourceNotFoundException("No such category exists");
        }
        for(CategoryDTO child : children) {
            if(categoryPersistenceLayer.getCategory(child.getCategoryId()) == null) {
                throw new ResourceNotFoundException("could not found child category : " + child.getName());
            };
        }
        category.setChildren(children);
        category = categoryPersistenceLayer.updateCategory(category, categoryId);
        CategoryDTO dto = new CategoryDTO();
        BeanUtils.copyProperties(category, dto);
        return dto;
    }
}