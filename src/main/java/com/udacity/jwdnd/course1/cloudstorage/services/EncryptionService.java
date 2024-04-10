package com.udacity.jwdnd.course1.cloudstorage.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Service
public class EncryptionService {
    private static final Logger logger = LoggerFactory.getLogger(EncryptionService.class);

    public String encryptValue(String data, byte[] key) {
        byte[] encryptedValue = null;

        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            SecretKey secretKey = new SecretKeySpec(key, "AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            encryptedValue = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException
                 | IllegalBlockSizeException | BadPaddingException e) {
            logger.error(e.getMessage());
        }

        return Base64.getEncoder().encodeToString(encryptedValue);
    }

    public String decryptValue(String data, String key) {
        byte[] decryptedValue = null;

        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            SecretKey secretKey = new SecretKeySpec(convertKeyStringToByteArray(key), "AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            decryptedValue = cipher.doFinal(Base64.getDecoder().decode(data));
        } catch (NoSuchAlgorithmException | NoSuchPaddingException
                | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            logger.error(e.getMessage());
        }

        return decryptedValue != null ? new String(decryptedValue): "";
    }

    public SecretKey generateRandomKey(String keyType, int keyLength) throws NoSuchAlgorithmException {
        KeyGenerator keyGen = KeyGenerator.getInstance(keyType);
        keyGen.init(keyLength);
        return keyGen.generateKey();
    }

    public String convertKeyByteArrayToString(byte[] keyArr) {
        StringBuilder keyString = new StringBuilder();
        for (byte byteItem: keyArr) {
            keyString.append(byteItem);
            keyString.append(":");
        }
        return keyString.toString();
    }

    public byte[] convertKeyStringToByteArray(String key) {
        byte[] keyByteArray = new byte[32];
        if (key != null) {
            String[] keyItem = key.split(":");
            for (int i = 0; i < keyItem.length; i++) {
                keyByteArray[i] = Byte.parseByte(keyItem[i]);
            }
        }
        return keyByteArray;
    }
}
