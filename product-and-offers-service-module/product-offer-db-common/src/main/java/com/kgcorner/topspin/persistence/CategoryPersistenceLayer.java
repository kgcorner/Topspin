package com.kgcorner.topspin.persistence;

import com.kgcorner.topspin.dtos.Category;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 29/04/21
 */

public interface CategoryPersistenceLayer {
    Category createCategory(Category category);
    Category getCategory(String id);
}