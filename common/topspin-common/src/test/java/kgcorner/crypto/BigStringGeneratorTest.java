package kgcorner.crypto;

import com.kgcorner.crypto.BigStringGenerator;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;


/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 18/11/19
 */

public class BigStringGeneratorTest {

    @Test
    public void generateBigString() {
        String token = BigStringGenerator.generateBigString();
        assertNotNull("generated token is null", token);
    }
}