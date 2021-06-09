package kgcorner.models;

import com.kgcorner.models.BaseToken;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 10/03/20
 */

public class BaseTokenTest {

    class TokenTestModel extends BaseToken {}

    private TokenTestModel token;

    @Before
    public void setUp() throws Exception {
        token = new TokenTestModel();
    }

    @Test
    public void testBaseToken() {
        token.setAccessToken("Access Token");
        token.setRefreshToken("Refresh Token");
        token.setExpiresInSeconds(100);
        assertEquals("Access Token", token.getAccessToken());
        assertEquals("Refresh Token", token.getRefreshToken());
        assertEquals(100, token.getExpiresInSeconds());
    }
}