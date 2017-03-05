package com.gv.cataloguer.cryptography;

import java.util.ResourceBundle;

public class CryptographerXOR implements Cryptographer {

    private static final Cryptographer INSTANCE = new CryptographerXOR();
    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("cryptoKey");
    private static final String KEY_PROPERTY_NAME = "key";

    private CryptographerXOR(){}

    public static Cryptographer getInstance(){
        return INSTANCE;
    }

    @Override
    public String encrypt(String str) {
        byte[] key = RESOURCE_BUNDLE.getString(KEY_PROPERTY_NAME).getBytes();
        byte[] text = str.getBytes();
        byte[] result = new byte[str.length()];
        for(int i = 0; i < text.length; i++){
            result[i] = (byte)(text[i] ^ key[i % key.length]);
        }
        return new String(result);
    }

    @Override
    public String decrypt(String str) {
        return encrypt(str);
    }
}
