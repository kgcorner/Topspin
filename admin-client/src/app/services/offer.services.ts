import { Injectable } from "@angular/core";
import { TopSpinHttpService } from './topspin.http';
import { environment } from 'src/environments/environment';
import { Offer } from './models/offer';

@Injectable()
export class OfferService {

    private GET_OFFERS = "/offers?page={1}&item-count={2}&includeBanners=true"
    private CREATE_OFFER = "/manage/offers"
    private UPDATE_OFFER = "/manage/offers/{offerId}"
    public constructor(private http: TopSpinHttpService){}

    getAllOffers(page, count) {
        let url = environment.topspinHost + this.GET_OFFERS;
        url = url.replace("{1}", page).replace("{2}",count);
        return this.http.doGet<Offer[]>(url, []);
    }

    createOffer(offer) {
        let url = environment.topspinHost + this.CREATE_OFFER;
        let formData = new FormData();
        for(let key in offer){
            formData.append(key, offer[key]);
        }
        return this.http.doPost<Offer>(url,[], offer)
    }

    deleteOffer(offerId) {
        let url = environment.topspinHost + this.UPDATE_OFFER;
        url = url.replace("{offerId}", offerId);
        return this.http.doDelete(url,[]);
    }

    addThumbnail(offerId, thumbnail) {
        let url = environment.topspinHost + this.UPDATE_OFFER;
        url = url.replace("{offerId}", offerId);
        let formData = new FormData();
        formData.append("image", thumbnail);
        return this.http.doPatch<Offer>(url, [], formData);
    }
}
