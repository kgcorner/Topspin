package com.kgcorner.topspin.client;

import com.kgcorner.topspin.model.Category;
import com.kgcorner.topspin.model.CategoryResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.reflect.Whitebox;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;


/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 01/05/21
 */

public class CategoryClientTest {

    private CategoryClient client;
    private CategoryResourceClient resourceClient;

    @Before
    public void setUp() throws Exception {
        resourceClient = PowerMockito.mock(CategoryResourceClient.class);
        client = new CategoryClient();
        Whitebox.setInternalState(client, "categoryResourceClient", resourceClient);
    }

    @Test
    public void getCategory() {
        ResponseEntity entity = PowerMockito.mock(ResponseEntity.class);
        String categoryId = "id";
        PowerMockito.when(resourceClient.get(categoryId)).thenReturn(entity);
        Category category = new CategoryResponse();
        PowerMockito.when(entity.getBody()).thenReturn(category);
        Assert.assertEquals(category, client.getCategory(categoryId));
    }

    @Test
    public void getAll() {
        ResponseEntity entity = PowerMockito.mock(ResponseEntity.class);
        int page = 0, items = 10;
        PowerMockito.when(resourceClient.getAll(page, items)).thenReturn(entity);
        Resources resources =  PowerMockito.mock(Resources.class);
        PowerMockito.when(entity.getBody()).thenReturn(resources);
        List<CategoryResponse> CategoryResponseS = new ArrayList<>();
        PowerMockito.when(resources.getContent()).thenReturn(CategoryResponseS);
        Assert.assertEquals(CategoryResponseS, client.getAll(page, items));
    }

    @Test
    public void create() {
        ResponseEntity entity = PowerMockito.mock(ResponseEntity.class);
        String name = "name";
        String description = "description";
        PowerMockito.when(resourceClient.create(name, description)).thenReturn(entity);
        Category category = new CategoryResponse();
        PowerMockito.when(entity.getBody()).thenReturn(category);
        Assert.assertEquals(category, client.create(name, description));
    }

    @Test
    public void update() {
        ResponseEntity entity = PowerMockito.mock(ResponseEntity.class);
        String name = "name";
        String description = "description";
        String id = "id";
        PowerMockito.when(resourceClient.update(id, name, description)).thenReturn(entity);
        Category category = new CategoryResponse();
        PowerMockito.when(entity.getBody()).thenReturn(category);
        Assert.assertEquals(category, client.update(id, name, description));
    }
}