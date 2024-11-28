package org.pollub.campusmate.utilities.security.config;

import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class PasswdGenerator {

    public static String generatePassword() {
        // Define rules for password generation
        CharacterRule lowerCaseRule = new CharacterRule(EnglishCharacterData.LowerCase, 1); // At least one lowercase letter
        CharacterRule upperCaseRule = new CharacterRule(EnglishCharacterData.UpperCase, 1); // At least one uppercase letter
        CharacterRule digitRule = new CharacterRule(EnglishCharacterData.Digit, 1);         // At least one digit
        CharacterRule specialCharRule = new CharacterRule(EnglishCharacterData.Special, 1); // At least one special character

        // Ensure password length is between 6 and 12
        int minPasswordLength = 6;
        int maxPasswordLength = 12;

        // Generate a random length within the specified range
        int passwordLength = new SecureRandom().nextInt(maxPasswordLength - minPasswordLength + 1) + minPasswordLength;

        PasswordGenerator passwordGenerator = new PasswordGenerator();

        // Generate password with the defined length and rules
        String password = passwordGenerator.generatePassword(passwordLength, lowerCaseRule, upperCaseRule, digitRule, specialCharRule);

        return password;
    }

    public static void main(String[] args) {
        // Example usage
        System.out.println(generatePassword());
    }
}
