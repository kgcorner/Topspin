package com.kgcorner.topspin.dtos.factory;


import com.kgcorner.topspin.dtos.Category;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 29/04/21
 */

public interface CategoryFactory {
    Category createCategory(String id, String name, String description);
}