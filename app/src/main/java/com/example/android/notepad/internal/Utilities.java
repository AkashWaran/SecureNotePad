package com.example.android.notepad.internal;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

/**
 * Created by Akash on 9/9/2014.
 */
public class Utilities implements IUtilities {

    private final static String HEX = "0123456789ABCDEF";

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

    public void whiteoutBytes(byte[] data) {
        for (int i=0; i<data.length; i++) {
            data[i] = 0;
        }
    }

    protected static byte[] toByte(String hexString) {
        int len = hexString.length() / 2;
        byte[] result = new byte[len];
        for (int i = 0; i < len; i++)
            result[i] = Integer.valueOf(hexString.substring(2 * i, 2 * i + 2), 16).byteValue();
        return result;
    }

    protected static String toHex(byte[] buffer) {
        if (buffer == null)
            return "";
        StringBuilder result = new StringBuilder(2 * buffer.length);
        for (int i = 0; i < buffer.length; i++) {
            appendHex(result, buffer[i]);
        }
        return result.toString();
    }

    private static void appendHex(StringBuilder buf, byte ch) {
        buf.append(HEX.charAt((ch >> 4) & 0x0f)).append(HEX.charAt(ch & 0x0f));
    }
}
