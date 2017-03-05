package com.gv.cataloguer.cryptography;

public interface Cryptographer {

    String encrypt(String str);

    String decrypt(String str);
}
