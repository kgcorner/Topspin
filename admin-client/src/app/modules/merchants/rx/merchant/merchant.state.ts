import {EntityState, EntityAdapter, createEntityAdapter} from '@ngrx/entity'
import { Merchant } from 'src/app/services/models/merchant';


export interface MerchantState extends EntityState<Merchant> {
    isLaoding: boolean,
    error:any,
    merchants: Merchant[],
    currectMerchant?: Merchant,
    merchantToEdit?: Merchant
}

export const merchantAdapter : EntityAdapter<Merchant> = createEntityAdapter<Merchant>({
    sortComparer: (m1: Merchant, m2: Merchant) => m1.name > m2.name ? 1 : -1
})
