import {EntityState, EntityAdapter, createEntityAdapter} from '@ngrx/entity'
import { Offer } from 'src/app/services/models/offer'


export interface OfferState extends EntityState<Offer> {
    isLaoding: boolean,
    error:any,
    offers: Offer[],
    currectOffer?: Offer,
    offerToEdit?: Offer
}

export const offerAdapter : EntityAdapter<Offer> = createEntityAdapter<Offer>({
    sortComparer: (m1: Offer, m2: Offer) => m1.offerId > m2.offerId ? 1 : -1
})
