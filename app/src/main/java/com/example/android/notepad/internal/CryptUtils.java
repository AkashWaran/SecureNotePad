package com.example.android.notepad.internal;

import android.content.Context;

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
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by Akash on 9/9/2014.
 */
public class CryptUtils implements ICryptUtils {

    private final static int HASH_SIZE = 20;
    private final static String CIPHER_TO_USE = "AES/CBC/PKCS5Padding";
    private final static int KEY_SIZE = 16;
    private static IUtilities utilities;

    public CryptUtils(Context mContext) {
        utilities = new Utilities(mContext);
    }

    public byte[] stringEncrypt(byte[] iv, byte[] key, byte[] salt, int id, String data) {
        if ((iv == null) || (iv.length == 0)) {
            iv = utilities.generateRandom(KEY_SIZE);
        }
        if ((salt == null) || (salt.length == 0)) {
            salt = utilities.generateRandom(KEY_SIZE);
        }
        if ((key == null) || (key.length == 0)) {
            byte[] temp = utilities.generateSeed();
            key = utilities.generateRawKey(temp);
            utilities.whiteoutBytes(temp);
        }
        byte[] result = encrypt(iv, key, (String.format("%08d", id) + data).getBytes());
        return utilities.appendBytes(generateHash(result, salt), result);
    }

    public String stringDecrypt(byte[] iv, byte[] key, byte[] salt, int id, byte[] data) {
        byte[] hash = new byte[HASH_SIZE];
        byte[] text = new byte[data.length - HASH_SIZE];
        if (data.length <= HASH_SIZE) {
            return null;
        }
        System.arraycopy(data, 0, hash, 0, HASH_SIZE);
        System.arraycopy(data, HASH_SIZE, text, 0, text.length);
        if (Arrays.equals(hash, generateHash(text, salt))) {
            String result = new String(decrypt(iv, key, text));
            if (Integer.parseInt(result.substring(0, 8)) == id) {
                return result.substring(8);
            }
            System.out.println("ERROR : Unique ID of file does not match given id");
            return null;
        }
        System.out.println("ERROR : Hash mismatch");
        return null;
    }

    public byte[] fileEncrypt(byte[] iv, byte[] key, byte[] salt, int id, String path) throws IOException {
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
        return stringEncrypt(iv, key, salt, id, data);
    }

    public String fileDecrypt(byte[] iv, byte[] key, byte[] salt, int id, String path) throws IOException {
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
        return stringDecrypt(iv, key, salt, id, data.getBytes());
    }

    private byte[] generateHash(byte[] text, byte[] salt) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        byte[] body = utilities.appendBytes(text, salt);
        md.update(body);
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
