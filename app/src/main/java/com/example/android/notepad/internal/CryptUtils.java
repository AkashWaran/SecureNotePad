package com.example.android.notepad.internal;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by Akash on 9/9/2014.
 */
public class CryptUtils extends Utilities implements ICryptUtils {

    private final static int HASH_SIZE = 40;
    private final static String CIPHER_TO_USE = "AES/CBC/PKCS5Padding";
    private final static int KEY_SIZE = 16;

    public String stringEncrypt(byte[] iv, byte[] key, byte[] salt, String data) {
        if (iv == null) {
            iv = generateRandom(KEY_SIZE);
        }
        if (salt == null) {
            salt = generateRandom(KEY_SIZE);
        }
        if (key == null) {
            byte[] temp = generateSeed();
            key = generateRawKey(temp);
            whiteoutBytes(temp);
        }
        String result = toHex(encrypt(iv, key, data.getBytes()));
        return (toHex(generateHash(result, salt)) + result);
    }

    public String stringDecrypt(byte[] iv, byte[] key, byte[] salt, String data) {
        if (data.length() <= HASH_SIZE) {
            return null;
        }
        String text = data.substring(0, HASH_SIZE);
        if (text.equals(toHex(generateHash(data.substring(HASH_SIZE), salt)))) {
            return new String(decrypt(iv, key, toByte(data.substring(HASH_SIZE))));
        }
        return null;
    }

    public String fileEncrypt(byte[] iv, byte[] key, byte[] salt, String path) throws IOException {
        String data = "", row;
        FileInputStream input = null;
        try {
            input = new FileInputStream(new File(path));
        } catch (FileNotFoundException e) {
            return null;
        }
        BufferedReader buffer = new BufferedReader(new InputStreamReader(input));

        while ((row = buffer.readLine()) != null) {
            data += row + "\n";
        }
        return stringEncrypt(iv, key, salt, data);
    }

    public String fileDecrypt(byte[] iv, byte[] key, byte[] salt, String path) throws IOException {
        String data = "", row;
        FileInputStream input = null;
        try {
            input = new FileInputStream(new File(path));
        } catch (FileNotFoundException e) {
            return null;
        }
        BufferedReader buffer = new BufferedReader(new InputStreamReader(input));

        while ((row = buffer.readLine()) != null) {
            data += row + "\n";
        }
        return stringDecrypt(iv, key, salt, data);
    }

    private static byte[] generateHash(String text, byte[] salt) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        md.update((text + salt.toString()).getBytes());
        return md.digest();
    }

    private static byte[] secureHash(byte[] data) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        md.update(data);
        return md.digest();
    }

    private static byte[] encrypt(byte[] iv, byte[] key, byte[] clear) {
        IvParameterSpec ivSpec = new IvParameterSpec(iv);
        SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance(CIPHER_TO_USE);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }

        byte[] encrypted = null;
        if (cipher != null) {
            try {
                cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
            } catch (InvalidKeyException e) {
                e.printStackTrace();
            } catch (InvalidAlgorithmParameterException e) {
                e.printStackTrace();
            }
            try {
                encrypted = cipher.doFinal(clear);
            } catch (IllegalBlockSizeException e) {
                e.printStackTrace();
            } catch (BadPaddingException e) {
                e.printStackTrace();
            }
        }
        return encrypted;
    }

    private static byte[] decrypt(byte[] iv, byte[] key, byte[] encrypted) {
        IvParameterSpec ivSpec = new IvParameterSpec(iv);
        SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance(CIPHER_TO_USE);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }

        byte[] decrypted = null;
        if (cipher != null) {
            try {
                cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
            } catch (InvalidKeyException e) {
                e.printStackTrace();
            } catch (InvalidAlgorithmParameterException e) {
                e.printStackTrace();
            }
            try {
                decrypted = cipher.doFinal(encrypted);
            } catch (IllegalBlockSizeException e) {
                e.printStackTrace();
            } catch (BadPaddingException e) {
                e.printStackTrace();
            }
        }
        return decrypted;
    }
}
