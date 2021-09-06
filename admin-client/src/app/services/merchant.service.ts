import { Injectable } from '@angular/core';
import { TopSpinHttpService } from './topspin.http';
import { Merchant } from './models/merchant';
import { environment } from 'src/environments/environment';

@Injectable()
export class MerchantManager {
    
    private GET_ALL_MERHCANTS = "/stores?page={1}&item-count={2}"
    private GET_MERCHANT = "/stores/{1}"
    private UPDATE_MERCHANT = "/manage/stores/{1}"
    private CREATE_MERCHANT = "/manage/stores"
    
    public constructor(private http : TopSpinHttpService) {
    }

    public getAllStores(page: number, maxItems: number) {
        let url : string = environment.topspinHost + this.GET_ALL_MERHCANTS;
        url = url.replace("{1}", page + "").replace("{2}", maxItems + "");
        return this.http.doGet<Merchant[]>(url,[]);
    }

    public createMerchant(merchant) {
        let url : string = environment.topspinHost + this.CREATE_MERCHANT;
        return this.http.doPost<Merchant>(url, [], merchant);
    }

    public updateMerchantImages(storeId, logo, thumbnail, banner) {
        let url : string = environment.topspinHost + this.UPDATE_MERCHANT;
        url = url.replace("{1}", storeId);
        let formData = new FormData();
        formData.append('banner', banner);
        formData.append('logo', logo);
        formData.append('thumbnail', thumbnail);
        return this.http.doPatch<Merchant>(url, [], formData);
    }

    public deleteMerchant(storeId) {
        let url : string = environment.topspinHost + this.UPDATE_MERCHANT;
        url = url.replace("{1}", storeId);
        return this.http.doDelete(url,[]);
    }

}