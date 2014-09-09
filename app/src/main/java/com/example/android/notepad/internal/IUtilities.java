package com.example.android.notepad.internal;

/**
 * Created by Akash on 9/9/2014.
 */
public interface IUtilities {

    /**
     *
     * This method can be used to generate a random byte array.
     * This is useful for generating salt or initializing vectors.
     *
     * Note : This method makes use of secure random
     *
     * @param size - The number of bytes of data you want to randomly generate
     *
     * @return Random byte array
     */
    public byte[] generateRandom(int size);

    /**
     *
     * This method is used to generate the key used for encryption.
     * Due to the nature of key we need this to be truely random and it is
     * thus generated using a seed provided by the user.
     * This is done to ensure randomness by using a turely random seed.
     *
     * Note : This function is used where the randomness of the result is of utmost importance
     *
     * @param seed - Random seed which we use to generate a random key.
     *
     * @return Random byte array
     */
    public byte[] generateRawKey(byte[] seed);
}
