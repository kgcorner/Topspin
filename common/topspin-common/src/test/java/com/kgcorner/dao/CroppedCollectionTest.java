package com.kgcorner.dao;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;


/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 08/09/21
 */

public class CroppedCollectionTest {

    private CroppedCollection<List<String>> croppedList;
    private List<String> list = new ArrayList<>();
    private int size = 100;
    @Before
    public void setup() {
        for (int i = 0; i < 10; i++) {
            list.add(i+"");
        }
        croppedList = new CroppedCollection<>(size, list);
    }
    @Test
    public void getSize() {
        Assert.assertEquals(size, croppedList.getSize());
    }

    @Test
    public void getItems() {
        assertEquals(list, croppedList.getItems());
    }
}