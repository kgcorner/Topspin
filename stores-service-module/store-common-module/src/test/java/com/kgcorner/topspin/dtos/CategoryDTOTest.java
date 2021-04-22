package com.kgcorner.topspin.dtos;

import com.kgcorner.topspin.model.AbstractCategory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.powermock.api.mockito.PowerMockito;


/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 17/04/21
 */

public class CategoryDTOTest {

    private CategoryDTO categoryDTO;
    private AbstractCategory category;
    @Before
    public void setUp() {
        category = PowerMockito.mock(AbstractCategory.class);
        categoryDTO = new CategoryDTO(category);
    }

    @Test
    public void getChildren() {
        int maxCount = 10;
        for (int i = 0; i < maxCount; i++) {
            categoryDTO.addChildren(new CategoryDTO(category));
        }
        Assert.assertEquals(maxCount, categoryDTO.getChildren().size());
    }
}