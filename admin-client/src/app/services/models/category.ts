export interface Category {
    name:            string;
    description:     string;
    longDescription: string;
    bannerImage:     string;
    thumbnailImage:  string;
    largeImage:      string;
    parent:          Category;
    tagline:         string;
    categoryId:      string;
    children:        any[];
}