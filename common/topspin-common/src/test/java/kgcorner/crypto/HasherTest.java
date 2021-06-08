package kgcorner.crypto;




/*
Description : <Write class Description>
Author: kumar
Created on : 03/09/19
*/

import com.kgcorner.crypto.Hasher;
import org.junit.Assert;
import org.junit.Test;

public class HasherTest {
    private static final String PASSWORD_TO_TEST = "password";
    private static final String PASSWORD_SALT_TEST = "salt";

    @Test
    public void getCrypt() {
        String hash = Hasher.getCrypt(PASSWORD_TO_TEST, PASSWORD_SALT_TEST);
        Assert.assertTrue(hash.startsWith("$2a$10"));
    }

    @Test
    public void checkPassword() {
        String hash = Hasher.getCrypt(PASSWORD_TO_TEST, PASSWORD_SALT_TEST);
        Assert.assertTrue(Hasher.checkPassword(PASSWORD_TO_TEST,  hash));
    }
}