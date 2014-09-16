package com.example.android.notepad.internal;

import java.io.IOException;

/**
 * Created by Akash on 9/9/2014.
 */
public interface ICryptUtils {

    /**
     *
     * This method is used to encrypt a notepad file, it automatically hashes the
     * result and stores it along with the file for integrity check.
     * Cipher used - AES block cipher (namely CBC)
     * Hashing function - SHA-1 Hash with 128 bits key
     *
     * @param iv    - Initialization vector required for cbc block cipher
     *                If null or empty then an IV is generated.
     * @param key   - Key used to encrypt string
     *                If null or empty then a key is generated.
     * @param salt  - salt used for hashing result requried for integrity check
     *                If null or empty  salt is generated.
     * @param id    - unique id of file
     * @param data  - string to be encrypted
     *
     * @return  Encrypted string as byte array
     *          Returns null on failure
     */
    public byte[] stringEncrypt(byte[] iv, byte[] key, byte[] salt, int id, String data);

    /**
     *
     * This method is used to decrypt a notepad file, it first verifies the hash
     * for integrity and if the hash matches proceeds to decrypt the string.
     * Cipher used - AES block cipher (namely CBC)
     * Hashing function - SHA-1 Hash with 128 bits key
     *
     * @param iv    - Initialization vector required for cbc block cipher
     * @param key   - Key used to decrypt string
     * @param salt  - salt used for hashing result requried for integrity check
     * @param id    - unique id of file
     * @param data  - byte array to be decrypted
     *
     * @return  Decrypted string
     *          Returns null on failure
     */
    public String stringDecrypt(byte[] iv, byte[] key, byte[] salt, int id, byte[] data);

    /**
     *
     * This method is used to encrypt a notepad file given path, it first verifies
     * for integrity and if the hash matches proceeds to encrypt the file.
     * Cipher used - AES block cipher (namely CBC)
     * Hashing function - SHA-1 Hash with 128 bits key
     *
     * @param iv    - Initialization vector required for cbc block cipher
     *                If null or empty then an IV is generated.
     * @param key   - Key used to decrypt string
     *                If null or empty then a key is generated.
     * @param salt  - salt used for hashing result requried for integrity check
     *                If null or empty then salt is generated.
     * @param id    - unique id of file
     * @param path  - Path of file to be encrypted
     *
     * @return  Encrypted string as byte array
     *          Returns null on failure
     *
     * @throws IOException
     */
    public byte[] fileEncrypt(byte[] iv, byte[] key, byte[] salt, int id, String path) throws IOException;

    /**
     *
     * This method is used to decrypt a notepad file given path, it first verifies
     * for integrity and if the hash matches proceeds to decrypt the file.
     * Cipher used - AES block cipher (namely CBC)
     * Hashing function - SHA-1 Hash with 128 bits key
     *
     * @param iv    - Initialization vector required for cbc block cipher
     * @param key   - Key used to decrypt string
     * @param salt  - salt used for hashing result requried for integrity check
     * @param id    - unique id of file
     * @param path  - Path of file to be decrypted
     *
     * @return  Decrypted string
     *          Returns null on failure
     *
     * @throws IOException
     */
    public String fileDecrypt(byte[] iv, byte[] key, byte[] salt, int id, String path) throws IOException;

    /**
     *
     * This method is used in order to generate a hash from the text
     *
     * @param text  - This parameter constitutes the data that needs to be hased
     * @param salt  - This parameter is the salt used to prevent identical data from forming identical hashes
     *
     * @return This method will return the hash of the text concatinated with the salt
     */
    public byte[] generateHash(byte[] text, byte[] salt);

    /**
     *
     * This method returns a hash purely based on the input.
     * This should not be used for storing but only in order to generate key from password.
     *
     * @param text - This is the data which is hashed
     *
     * @return hash of entered data
     */
    public byte[] secureHash(byte[] text);

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

    /**
     *
     * This method returns an instance of utilities
     *
     * @return instance of utilities class
     */
    public IUtilities getUtilityInstance();
}
