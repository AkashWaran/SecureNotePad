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
     * @param data  - String to be encrypted
     *
     * @return  Encrypted string
     *          Returns null on failure
     */
    public String stringEncrypt(byte[] iv, byte[] key, byte[] salt, int id, String data);

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
     * @param data  - String to be decrypted
     *
     * @return  Decrypted string
     *          Returns null on failure
     */
    public String stringDecrypt(byte[] iv, byte[] key, byte[] salt, int id, String data);

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
     * @return  Encrypted string
     *          Returns null on failure
     *
     * @throws IOException
     */
    public String fileEncrypt(byte[] iv, byte[] key, byte[] salt, int id, String path) throws IOException;

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
}
