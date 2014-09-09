package com.example.android.notepad.internal;

/**
 * Created by Akash on 9/9/2014.
 */
public interface IUtilities {
    public byte[] generateRandom(int size);
    public byte[] generateRawKey(byte[] seed);
}
