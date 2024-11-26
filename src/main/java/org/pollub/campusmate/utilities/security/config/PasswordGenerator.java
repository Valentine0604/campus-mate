package org.pollub.campusmate.utilities.security.config;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;


public class PasswordGenerator {
    public static String generatePassword() throws NoSuchAlgorithmException {
        int passowrdLength = SecureRandom.getInstanceStrong().nextInt(7) + 6;
        StringBuilder password = new StringBuilder();

        return null; //todo
    }
}
