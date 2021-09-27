package com.kgcorner.topspin.persistence;


import com.kgcorner.dao.Operation;
import com.kgcorner.topspin.dao.MysqlOfferDao;
import com.kgcorner.topspin.model.*;
import com.kgcorner.utils.Strings;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 13/09/21
 */

@Transactional
@Repository
public class MysqlOfferPersistenceLayer implements OfferPersistenceLayer {

    @Autowired
    private MysqlOfferDao<OfferModel> offerDao;

    @Override
    public AbstractOffer getOffer(String offerId) {
        return offerDao.get(offerId, OfferModel.class);
    }

    @Override
    public AbstractOffer createOffer(AbstractOffer offer) {
        OfferModel offerModel = new OfferModel();
        BeanUtils.copyProperties(offer, offerModel);
        offerModel.setOfferId(Strings.getGuid());
        return offerDao.create(offerModel);
    }

    @Override
    public AbstractOffer updateOffer(AbstractOffer offer) {
        OfferModel offerModel = (OfferModel) getOffer(offer.getOfferId());
        BeanUtils.copyProperties(offer, offerModel);
        return offerDao.update(offerModel);
    }

    @Override
    public List<AbstractOffer> getAll(int page, int itemPerPage, boolean onlyFeatured, StoreRef store,
                                      CategoryRef category, boolean includeBanners) {
        List<Operation> operands = new ArrayList<>();
        operands.add(new Operation(new Date(), Date.class, "lastDate", Operation.OPERATORS.GE));
        if(onlyFeatured)
            operands.add(new Operation(true, Operation.TYPES.BOOLEAN, "featured", Operation.OPERATORS.EQ));
        if(store != null) {
            operands.add(new Operation(store, StoreReferenceModel.class, "store", Operation.OPERATORS.EQ));
        }
        if(!includeBanners)
            operands.add(new Operation(false, Operation.TYPES.BOOLEAN, "banner", Operation.OPERATORS.EQ));
        if(category != null) {
            operands.add(new Operation(category, CategoryReferenceModel.class, "category", Operation.OPERATORS.EQ));
        }
        List<OfferModel> models = offerDao.getAll(operands, page, itemPerPage, OfferModel.class);
        return createOfferList(models);
    }

    @Override
    public List<AbstractOffer> getAll(int page, int itemPerPage) {
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
    public List<AbstractOffer> getAllOfferFromCategory(CategoryRef category, int page, int itemsPerPage) {
        if(!(category instanceof CategoryReferenceModel)) {
            throw new IllegalArgumentException("Unexpected Category type");
        }
        List<Operation> operations = new ArrayList<>();
        operations.add(new Operation(category, CategoryReferenceModel.class,
            "category", Operation.OPERATORS.EQ));
        operations.add(new Operation(new Date(), Date.class, "lastDate", Operation.OPERATORS.GE));
        List<OfferModel> models = offerDao.getAll(operations, page, itemsPerPage, null, OfferModel.class);
        return createOfferList(models);
    }

    @Override
    public List<AbstractOffer> getAllOfferFromStore(StoreRef store, int page, int itemsPerPage) {
        if(!(store instanceof StoreReferenceModel)) {
            throw new IllegalArgumentException("Unexpected store type");
        }
        List<Operation> operations = new ArrayList<>();
        operations.add(new Operation(store, StoreReferenceModel.class,
            "store", Operation.OPERATORS.EQ));
        operations.add(new Operation(new Date(), Date.class, "lastDate", Operation.OPERATORS.GE));
        List<OfferModel> models = offerDao.getAll(operations, page, itemsPerPage, null, OfferModel.class);
        return createOfferList(models);
    }

    @Override
    public List<AbstractOffer> getBanners() {
        List<Operation> operands = new ArrayList<>();
        operands.add(new Operation(new Date(), Date.class, "lastDate", Operation.OPERATORS.GE));
        operands.add(new Operation(true, Operation.TYPES.BOOLEAN, "banner", Operation.OPERATORS.EQ));

        List<OfferModel> allBanners = offerDao.getAll(operands, OfferModel.class);
        return createOfferList(allBanners);
    }

    private List<AbstractOffer> createOfferList(List<OfferModel> models) {
        List<AbstractOffer> offers = new ArrayList<>();
        for(AbstractOffer o : models) {
            offers.add(o);
        }
        return offers;
    }
}