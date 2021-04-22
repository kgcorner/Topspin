package com.kgcorner.topspin.dtos;

import com.kgcorner.topspin.model.AbstractStore;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 17/04/21
 */

public class StoreDTOTest {

    private StoreDTO storeDTO;
    private AbstractStore store;
    @Before
    public void setUp() {
        storeDTO = new StoreDTO(store);
    }



    @Test
    public void setCategories() {
        List<CategoryDTO> categoryDTOS = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            CategoryDTO categoryDTO = new CategoryDTO(null);
            categoryDTOS.add(categoryDTO);
            storeDTO.addCategories(categoryDTO);
        }

        Assert.assertEquals(categoryDTOS, storeDTO.getCategories());
    }
}