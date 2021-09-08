import { Category } from "./category";
import { Link } from './link';

export interface Merchant {
    name:              string;
    link:              string;
    logo:              string;
    openOut:           boolean;
    affiliated:        boolean;
    placeHolder:       string;
    surferPlaceHolder: string;
    searchUrl:         string;
    pidRegex:          null;
    gender:            string;
    affiliateId:       string;
    maxCashback:       string;
    description:       string;
    longDescription:   string;
    bannerImage:       string;
    thumbnailImage:    string;
    tagLine:           string;
    active:            boolean;
    storeId:           string;
    categories:        Category[];
    links:             Link[];
}