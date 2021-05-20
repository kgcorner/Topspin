package com.kgcorner.topspin.persistence;


import com.kgcorner.dao.Operation;
import com.kgcorner.topspin.dao.MysqlOfferDao;
import com.kgcorner.topspin.dtos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 29/04/21
 */

@Repository
public class MysqlOfferPersistenceLayer implements OfferPersistenceLayer {

    @Autowired
    private MysqlOfferDao<OfferModel> offerDao;

    @Override
    public Offer getOffer(String offerId) {
        return offerDao.get(offerId, OfferModel.class);
    }

    @Override
    public Offer createOffer(Offer offer) {
        return offerDao.create((OfferModel) offer);
    }

    @Override
    public Offer updateOffer(Offer offer) {
        return offerDao.update((OfferModel) offer);
    }

    @Override
    public List<Offer> getAll(int page, int itemPerPage) {
        List<OfferModel> models = offerDao.getAll(page, itemPerPage, OfferModel.class);
        return createOfferList(models);
    }

    @Override
    public void deleteOffer(String offerId) {
        OfferModel offerModel = (OfferModel) getOffer(offerId);
        if(offerModel == null)
            throw new IllegalArgumentException("No such offer exists");
        offerDao.remove(offerModel);
    }

    @Override
    public List<Offer> getAllOfferFromCategory(Category category, int page, int itemsPerPage) {
        if(!(category instanceof CategoryReferenceModel)) {
            throw new IllegalArgumentException("Unexpected Category type");
        }
        List<Operation> operations = new ArrayList<>();
        operations.add(new Operation(category, CategoryReferenceModel.class,
            "category", Operation.OPERATORS.EQ));
        List<OfferModel> models = offerDao.getAll(operations, page, itemsPerPage, null, OfferModel.class);
        return createOfferList(models);
    }

    @Override
    public List<Offer> getAllOfferFromStore(Store store, int page, int itemsPerPage) {
        if(!(store instanceof StoreReferenceModel)) {
            throw new IllegalArgumentException("Unexpected store type");
        }
        List<Operation> operations = new ArrayList<>();
        operations.add(new Operation(store, StoreReferenceModel.class,
            "store", Operation.OPERATORS.EQ));
        List<OfferModel> models = offerDao.getAll(operations, page, itemsPerPage, null, OfferModel.class);
        return createOfferList(models);
    }

    private List<Offer> createOfferList(List<OfferModel> models) {
        List<Offer> offers = new ArrayList<>();
        for(Offer o : models) {
            offers.add(o);
        }
        return offers;
    }
}