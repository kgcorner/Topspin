import { Category } from './category';
import { Merchant } from './merchant';
import { Link } from './link';

export class Offer {
    title: string;
    description: string;
    thumbnails: string;
    url: string;
    surferPlaceholderUrl: string;
    category: Category;
    store: Merchant;
    lastDate: Date;
    maxDiscount: string;
    featured: boolean;
    offerId : string;
    links: Link[];
}