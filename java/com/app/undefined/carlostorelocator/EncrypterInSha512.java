package com.app.undefined.carlostorelocator;

/**
 * Created by carlo on 15/04/2016.
 */
import android.util.Base64;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncrypterInSha512 {
public static String GenerateHash(String input) throws NoSuchAlgorithmException {
        MessageDigest objSHA = MessageDigest.getInstance("SHA-512");
        byte[] bytSHA = objSHA.digest(input.getBytes());
        String generatePassword = Base64.encodeToString(bytSHA, Base64.NO_WRAP);
        return generatePassword;
}
}
