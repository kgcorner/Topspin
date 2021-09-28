package com.kgcorner.topspin.resources;

import com.kgcorner.topspin.dtos.OfferDTO;
import com.kgcorner.topspin.model.StoreRef;
import com.kgcorner.topspin.services.OfferService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.reflect.Whitebox;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;


/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 18/09/21
 */

public class OfferResourceTest {

    private OfferService offerService;
    private OfferResource offerResource;

    private static final String OFFERS_OFFER_ID = "/offers/{offerId}";
    private static final String MANAGE_OFFERS_OFFER_ID = "/manage/offers/{offerId}";

    @Before
    public void setUp() throws Exception {
        offerResource = new OfferResource();
        offerService = mock(OfferService.class);
        Whitebox.setInternalState(offerResource, "offerService", offerService);
    }

    @Test
    public void getOffers() {
        int page = 0;
        int count = 100;
        boolean onlyFeatured = true;
        String categoryId = "categoryId";
        String storeId = "storeId";
        List<OfferDTO> offerDTOS = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            OfferDTO offerDTO = new OfferDTO();
            offerDTO.setOfferId(i+"");
            offerDTOS.add(offerDTO);
        }
        when(offerService.getAllOffers(page, count, onlyFeatured, storeId, categoryId,
            false)).thenReturn(offerDTOS);
        ResponseEntity<Resources<OfferDTO>> offers = offerResource.getOffers(page, count, onlyFeatured, storeId,
            categoryId, false);
        assertNotNull(offers);
        assertEquals(count, offers.getBody().getContent().size());
    }

    @Test
    public void getOffer() {
        String offerId = "offerId";
        OfferDTO offerDTO = new OfferDTO();
        offerDTO.setOfferId(offerId);
        when(offerService.getOffer(offerId)).thenReturn(offerDTO);
        ResponseEntity<OfferDTO> offer = offerResource.getOffer(offerId);
        String link = OFFERS_OFFER_ID.replace("{offerId}", offerId);
        assertEquals(offerId, offer.getBody().getOfferId());
        assertEquals(link, offer.getBody().getLinks().get(0).getHref());
    }

    @Test
    public void createOffer() {
        OfferDTO offerDTO = new OfferDTO();
        String offerId = "offerId";
        when(offerService.createOffer(offerDTO)).thenAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                offerDTO.setOfferId(offerId);
                return offerDTO;
            }
        });
        String link = OFFERS_OFFER_ID.replace("{offerId}", offerId);
        ResponseEntity<OfferDTO> offer = offerResource.createOffer(offerDTO);
        assertEquals(offerId, offer.getBody().getOfferId());
        assertEquals(link, offer.getBody().getLinks().get(0).getHref());
    }

    @Test
    public void updateOffer() {
        OfferDTO offerDTO = new OfferDTO();
        String offerId = "offerId";
        offerDTO.setOfferId(offerId);
        when(offerService.updateOffer(offerDTO)).thenReturn(offerDTO);
        String link = OFFERS_OFFER_ID.replace("{offerId}", offerId);
        ResponseEntity<OfferDTO> offer = offerResource.updateOffer(offerId, offerDTO);
        assertEquals(offerId, offer.getBody().getOfferId());
        assertEquals(link, offer.getBody().getLinks().get(0).getHref());
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateOfferInvalidRequest() {
        OfferDTO offerDTO = new OfferDTO();
        String offerId = "offerId";
        offerDTO.setOfferId(offerId);
        when(offerService.updateOffer(offerDTO)).thenReturn(offerDTO);
        offerResource.updateOffer("invalid id", offerDTO);
    }

    @Test
    public void deleteOffer() {
        String offerId = "offerId";
        ResponseEntity<Void> voidResponseEntity = offerResource.deleteOffer(offerId);
        Mockito.verify(offerService).deleteOffer(offerId);
        assertEquals(HttpStatus.NO_CONTENT, voidResponseEntity.getStatusCode());
    }

    @Test
    public void addThumbnails() throws IOException {
        String offerId = "offerId";
        MultipartFile multipartFile = mock(MultipartFile.class);
        OfferDTO offerDTO = new OfferDTO();
        offerDTO.setOfferId(offerId);
        when(offerService.uploadImage(offerId, multipartFile)).thenReturn(offerDTO);
        ResponseEntity<OfferDTO> offerDTOResponseEntity = offerResource.addThumbnails(offerId, multipartFile);
        assertEquals(HttpStatus.OK, offerDTOResponseEntity.getStatusCode());
        assertEquals(offerDTO, offerDTOResponseEntity.getBody());
    }

    @Test(expected = IllegalArgumentException.class)
    public void addThumbnailsWithException() throws IOException {
        String offerId = "offerId";
        MultipartFile multipartFile = mock(MultipartFile.class);
        when(offerService.uploadImage(offerId, multipartFile)).thenThrow(IOException.class);
        offerResource.addThumbnails(offerId, multipartFile);
    }

    @Test
    public void getOffersFromStore() {
        int page = 1;
        int count = 100;
        boolean onlyFeatured = true;
        String storeId = "storeId";
        List<OfferDTO> offerDTOS = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            OfferDTO offerDTO = new OfferDTO();
            offerDTO.setOfferId(i+"");
            offerDTOS.add(offerDTO);
        }
        when(offerService.getOffersOfStores(storeId, page, count)).thenReturn(offerDTOS);
        ResponseEntity<Resources<OfferDTO>> offersFromStore = offerResource.getOffersFromStore(storeId, page, count);
        assertNotNull(offersFromStore);
        assertEquals(count, offersFromStore.getBody().getContent().size());
    }

    @Test
    public void getOffersFromCategory() {
        int page = 0;
        int count = 100;
        boolean onlyFeatured = true;
        String categoryId = "categoryId";
        List<OfferDTO> offerDTOS = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            OfferDTO offerDTO = new OfferDTO();
            offerDTO.setOfferId(i+"");
            offerDTOS.add(offerDTO);
        }
        when(offerService.getOffersOfCategory(categoryId, page, count)).thenReturn(offerDTOS);
        ResponseEntity<Resources<OfferDTO>> offersFromStore = offerResource.getOffersFromCategory(categoryId, page, count);
        assertNotNull(offersFromStore);
        assertEquals(count, offersFromStore.getBody().getContent().size());
    }

    @Test
    public void getBanners() {
        List<OfferDTO> banners = new ArrayList<>();
        int size = 100;
        for (int i = 0; i < size; i++) {
            OfferDTO offerDTO = new OfferDTO();
            offerDTO.setOfferId(i+"");
            banners.add(offerDTO);
        }
        when(offerService.getBanners()).thenReturn(banners);
        ResponseEntity<Resources<OfferDTO>> banners1 = offerResource.getBanners();
        assertNotNull(banners1);
        assertEquals(size, banners1.getBody().getContent().size());
    }

    @Test
    public void getStores() {
        List<StoreRef> stores = new ArrayList<>();
        for(int i = 0; i<9; i++) {
            stores.add(new StoreRef());
        }
        when(offerService.getStores()).thenReturn(stores);
        ResponseEntity<Resources<StoreRef>> storesResponse = offerResource.getStores();
        Collection<StoreRef> content = storesResponse.getBody().getContent();
        assertEquals(stores.size(), content.size());
    }
}