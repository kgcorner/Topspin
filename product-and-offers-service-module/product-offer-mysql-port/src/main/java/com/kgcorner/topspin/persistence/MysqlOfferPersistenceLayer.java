package com.kgcorner.topspin.persistence;


import com.kgcorner.topspin.dao.MysqlOfferDao;
import com.kgcorner.topspin.dtos.Offer;
import com.kgcorner.topspin.dtos.OfferModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
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
        return offerDao.getById(offerId, OfferModel.class);
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
        List<OfferModel> all = offerDao.getAll(page, itemPerPage, OfferModel.class);
        List<Offer> offers = new ArrayList<>();
        for(Offer offer : all) {
            offers.add(offer);
        }
        return offers;
    }

    @Override
    public void deleteOffer(String offerId) {
        OfferModel offerModel = (OfferModel) getOffer(offerId);
        if(offerModel == null)
            throw new IllegalArgumentException("No such offer exists");
        offerDao.remove(offerModel);
    }

    @Override
    public List<Offer> getAllOfferFromCategory(String categoryId, int page, int itemsPerPage) {
        String hql = "from offer where category=:category";
        Query query = this.offerDao.getEntityManager().createQuery(hql);
        query.setParameter("category", categoryId);
        int first = page * itemsPerPage;
        query.setFirstResult(first).setMaxResults(itemsPerPage);
        return query.getResultList();
    }

    @Override
    public List<Offer> getAllOfferFromStore(String storeId, int page, int itemsPerPage) {
        String hql = "from offer where store =:store";
        Query query = this.offerDao.getEntityManager().createQuery(hql);
        query.setParameter("store", storeId);
        int first = page * itemsPerPage;
        query.setFirstResult(first).setMaxResults(itemsPerPage);
        return query.getResultList();
    }
}