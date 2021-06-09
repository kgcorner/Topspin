package kgcorner.utils;

import com.kgcorner.utils.Strings;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 19/11/19
 */

public class StringsTest {

    @Test
    public void nullStringTest() {
        assertTrue(Strings.isNullOrEmpty(null));
    }
    @Test
    public void blankStringTest() {
        assertTrue(Strings.isNullOrEmpty(""));
    }

    @Test
    public void spaceTest() {
        assertTrue(Strings.isNullOrEmpty(" "));
    }

    @Test
    public void nonNullTest() {
        assertFalse(Strings.isNullOrEmpty("a"));
    }

}