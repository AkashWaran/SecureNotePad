package com.example.android.notepad.internal;

import java.io.IOException;

/**
 * Created by Akash on 9/9/2014.
 */
public interface ICryptUtils {

    public String stringEncrypt(byte[] iv, byte[] key, String data, String salt);
    public String stringDecrypt(byte[] iv, byte[] key, String data, String salt);
    public String fileEncrypt(byte[] iv, byte[] key, String path, String salt) throws IOException;
    public String fileDecrypt(byte[] iv, byte[] key, String path, String salt) throws IOException;
}
