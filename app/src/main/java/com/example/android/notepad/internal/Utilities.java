package com.example.android.notepad.internal;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

/**
 * Created by Akash on 9/9/2014.
 */
public class Utilities implements IUtilities {
    public byte[] generateRandom(int size) {
        SecureRandom random = new SecureRandom();
        byte iv[] = new byte[size];
        random.nextBytes(iv);
        return iv;
    }

    public byte[] generateRawKey(byte[] seed) {
        KeyGenerator kgen = null;
        SecureRandom sr = null;
        try {
            kgen = KeyGenerator.getInstance("AES");
            sr = SecureRandom.getInstance("SHA1PRNG");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        sr.setSeed(seed);
        kgen.init(128, sr);
        SecretKey skey = kgen.generateKey();
        byte[] raw = skey.getEncoded();
        return raw;
    }
}
