package com.gv.cataloguer.cryptography;

import org.junit.Assert;
import org.junit.Test;

public class CryptographerXORTest {

    @Test
    public void encryptAndDecrypt() throws Exception {
        String originStr = "44447777";
        Cryptographer cryptographer = CryptographerXOR.getInstance();
        String encryptStr = cryptographer.encrypt(originStr);
        Assert.assertEquals(originStr, cryptographer.decrypt(encryptStr));

        originStr = "";
        encryptStr = cryptographer.encrypt(originStr);
        Assert.assertEquals(originStr, cryptographer.decrypt(encryptStr));
    }
}