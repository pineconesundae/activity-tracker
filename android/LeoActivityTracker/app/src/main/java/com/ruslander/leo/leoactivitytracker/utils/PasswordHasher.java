package com.ruslander.leo.leoactivitytracker.utils;

import java.security.MessageDigest;
import java.util.Random;

public class PasswordHasher {
    /**
     * Hashes a password and a salt using the SHA-512 algorithm.
     *
     * @param password  The password to hash
     * @param salt      The salt to append to the password before hashing
     * @return The hashed password as a String
     */
    public static String hashPassword(final String password, String salt) {
        if (salt == null || salt.isEmpty()) {
            return null;
        }

        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(salt.getBytes("UTF-8"));
            byte[] bytes = md.digest(password.getBytes("UTF-8"));

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return generatedPassword;
    }

    /**
     * Gets a random salt value from 0 to 2^32.
     *
     * @return A salt value as a String
     */
    public static String getSalt() {
        Random random = new Random();
        return String.valueOf(random.nextInt());
    }
}
