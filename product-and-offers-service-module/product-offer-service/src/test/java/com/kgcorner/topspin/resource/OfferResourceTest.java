package com.kgcorner.topspin.resource;

import com.kgcorner.topspin.aws.AwsServices;
import com.kgcorner.topspin.dtos.OfferDTO;
import com.kgcorner.topspin.service.OfferService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.powermock.api.mockito.PowerMockito.*;


/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 12/08/21
 */

@RunWith(PowerMockRunner.class)
@PrepareForTest({OfferResource.class, AwsServices.class})
public class OfferResourceTest {

    private OfferService offerService;
    private OfferResource offerResource;
    private OfferResource spy;
    private String s3BucketName = "bucketName";
    private String bucketUrl = "bucketUrl";
    private AwsServices awsServices;

    @Before
    public void setUp() throws Exception {
        offerService = mock(OfferService.class);
        offerResource = new OfferResource();
        spy = spy(offerResource);
        awsServices = mock(AwsServices.class);
        mockStatic(AwsServices.class);
        when(AwsServices.getInstance()).thenReturn(awsServices);
        Whitebox.setInternalState(spy, "offerService", offerService);
        Whitebox.setInternalState(spy,"bucketUrl", bucketUrl);
        Whitebox.setInternalState(spy,"s3BucketName", s3BucketName);
    }

    @Test
    public void createOffer() throws Exception {
        String title = "offerTitle";
        String description = "description";
        Date lastDate = new Date();
        String categoryId = "categoryId";
        String storeId = "storeId";
        String url = "url";
        String maxDiscount = "90%";
        String thumbNail = "thumbNail";
        boolean featured = true;
        OfferDTO offer = mock(OfferDTO.class);
        when(offerService.createOffer(title, description, lastDate, categoryId, storeId, url, maxDiscount,
            thumbNail, featured)).thenReturn(offer);
        ResponseEntity mockedResponseEntity = mock(ResponseEntity.class);
        doReturn(mockedResponseEntity).when(spy, "getOfferDTOResponseEntity", offer,
            HttpStatus.CREATED);
        ResponseEntity<OfferDTO> response = spy.createOffer(title, description, lastDate, categoryId, storeId, url, maxDiscount,
            thumbNail, featured);
        assertEquals(mockedResponseEntity, response);
    }

    @Test
    public void testCreateOfferWithFile() throws Exception {
        String title = "offerTitle";
        String description = "description";
        Date lastDate = new Date();
        String categoryId = "categoryId";
        String storeId = "storeId";
        String url = "url";
        String maxDiscount = "90%";
        MultipartFile thumbNail = mock(MultipartFile.class);
        boolean featured = true;
        InputStream stream = mock(InputStream.class);
        OfferDTO offer = mock(OfferDTO.class);
        when(thumbNail.getOriginalFilename()).thenReturn("testfile.jpeg");
        when(thumbNail.getSize()).thenReturn(1024L);
        when(thumbNail.getInputStream()).thenReturn(stream);
        when(awsServices.sanitizeFileName(title)).thenReturn(title);
        String imageFile = bucketUrl + "/" + title +".jpeg";
        when(offerService.createOffer(title, description, lastDate, categoryId, storeId, url, maxDiscount,
            imageFile, featured)).thenReturn(offer);
        doNothing().when(awsServices).storeImage(s3BucketName, title + ".jpeg", stream, 1024L);
        ResponseEntity mockedResponseEntity = mock(ResponseEntity.class);
        doReturn(mockedResponseEntity).when(spy, "getOfferDTOResponseEntity", offer,
            HttpStatus.CREATED);
        ResponseEntity<OfferDTO> response = spy.createOffer(title, description, lastDate, categoryId, storeId, url, maxDiscount,
            thumbNail, featured);
        assertEquals(mockedResponseEntity, response);
    }

    @Test
    public void testCreateOfferWithBigNameFile() throws Exception {
        String title = "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu, pretium";
        String description = "description";
        Date lastDate = new Date();
        String categoryId = "categoryId";
        String storeId = "storeId";
        String url = "url";
        String maxDiscount = "90%";
        MultipartFile thumbNail = mock(MultipartFile.class);
        boolean featured = true;
        String tempFileName = title.substring(0, 59);
        InputStream stream = mock(InputStream.class);
        OfferDTO offer = mock(OfferDTO.class);
        when(thumbNail.getOriginalFilename()).thenReturn("testfile.jpeg");
        when(thumbNail.getSize()).thenReturn(1024L);
        when(thumbNail.getInputStream()).thenReturn(stream);
        when(awsServices.sanitizeFileName(tempFileName)).thenReturn(tempFileName);
        String imageFile = bucketUrl + "/" + tempFileName +".jpeg";
        when(offerService.createOffer(title, description, lastDate, categoryId, storeId, url, maxDiscount,
            imageFile, featured)).thenReturn(offer);
        doNothing().when(awsServices).storeImage(s3BucketName, title + ".jpeg", stream, 1024L);
        ResponseEntity mockedResponseEntity = mock(ResponseEntity.class);
        doReturn(mockedResponseEntity).when(spy, "getOfferDTOResponseEntity", offer,
            HttpStatus.CREATED);
        ResponseEntity<OfferDTO> response = spy.createOffer(title, description, lastDate, categoryId, storeId, url, maxDiscount,
            thumbNail, featured);
        assertEquals(mockedResponseEntity, response);
    }

    @Test
    public void get() throws Exception {
        String offerId = "offerId";
        OfferDTO offer = mock(OfferDTO.class);
        when(offerService.getOffer(offerId)).thenReturn(offer);
        ResponseEntity mockedResponseEntity = mock(ResponseEntity.class);
        doReturn(mockedResponseEntity).when(spy, "getOfferDTOResponseEntity", offer,
            HttpStatus.OK);
        ResponseEntity<OfferDTO> offerDTOResponseEntity = spy.get(offerId);
        assertEquals(mockedResponseEntity, offerDTOResponseEntity);
    }

    @Test
    public void updateOffer() throws Exception {
        String title = "offerTitle";
        String description = "description";
        Date lastDate = new Date();
        String categoryId = "categoryId";
        String storeId = "storeId";
        String url = "url";
        String maxDiscount = "90%";
        String offerId = "offerId";
        String thumbNail = "thumbNail";
        boolean featured = true;
        OfferDTO offer = mock(OfferDTO.class);
        when(offerService.updateOffer(offerId, title, description, lastDate, categoryId, storeId, url, maxDiscount,
            thumbNail, featured)).thenReturn(offer);
        ResponseEntity mockedResponseEntity = mock(ResponseEntity.class);
        doReturn(mockedResponseEntity).when(spy, "getOfferDTOResponseEntity", offer,
            HttpStatus.OK);
        ResponseEntity<OfferDTO> response = spy.updateOffer(offerId, title, description, lastDate, categoryId, storeId, url, maxDiscount,
            thumbNail, featured);
        assertEquals(mockedResponseEntity, response);
    }

    @Test
    public void deleteOffer() {
        String offerID = "offerid";
        doNothing().when(offerService).deleteOffer(offerID);
        ResponseEntity<Object> objectResponseEntity = spy.deleteOffer(offerID);
        assertEquals(HttpStatus.NO_CONTENT.value(), objectResponseEntity.getStatusCode().value());
    }
}