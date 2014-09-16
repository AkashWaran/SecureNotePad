package com.example.android.notepad.internal;

/**
 * Created by Akash on 9/9/2014.
 */
public interface IUtilities {

    /**
     *
     * This method is used in order to delete sensitive data once used.
     * We replace all data in the array with 0 so that after garbage collection
     * we do not have sensitive data stored in memory which can be exploited.
     *
     * @param data - The data which is required to be erased securely
     */
    public void whiteoutData(byte[] data);

    /**
     *
     * This method is used in order to delete sensitive data once used.
     * We replace all data in the array with 0 so that after garbage collection
     * we do not have sensitive data stored in memory which can be exploited.
     *
     * @param data - The data which is required to be erased securely
     */
    public void whiteoutChar(char[] data);

    /**
     * This method generates a random seed.
     * TO BE COMPLETED BY RAJ
     *
     * @return A random byte array generated using hardware components
     */
    public byte[] generateSeed();

    /**
     *
     * This method concatinates one byte array onto another
     *
     * @param firstArray    - The byte array which makes up the start of the concatination
     * @param secondArray   - The byte array which makes up the end of the concatination
     *
     * @return The concatinated byte array
     */
    public byte[] appendBytes(byte[] firstArray, byte[] secondArray);

    /**
     *
     * This method is used to convert a hexadecimal string into a byte array
     *
     * @param hexString - Hexadecimal string
     *
     * @return byte array representation of given hex string
     */
    public byte[] toByte(String hexString);

    /**
     *
     * This method returns a hexadecimal string of byte array
     *
     * @param buffer - This is the input byte array
     *
     * @return hexadecimal notation of the above array as a string
     */
    public String toHex(byte[] buffer);
}
