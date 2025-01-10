package org.pollub.campusmate.utilities.security.config;

import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;
import org.passay.CharacterData;

import java.security.SecureRandom;

public class PasswdGenerator {

    public static String generatePassword() {

        CharacterData specialCharacterData = new CharacterData() {
            @Override
            public String getErrorCode() {
                return "INSUFFICIENT_SPECIAL";
            }

            @Override
            public String getCharacters() {
                return "-_@$!%*?&";
            }
        };

        // Define rules for password generation
        CharacterRule lowerCaseRule = new CharacterRule(EnglishCharacterData.LowerCase, 1);
        CharacterRule upperCaseRule = new CharacterRule(EnglishCharacterData.UpperCase, 1);
        CharacterRule digitRule = new CharacterRule(EnglishCharacterData.Digit, 1);
        CharacterRule specialCharRule = new CharacterRule(specialCharacterData, 1);

        // Ensure password length is between 6 and 12
        int minPasswordLength = 8;
        int maxPasswordLength = 12;

        // Generate a random length within the specified range
        int passwordLength = new SecureRandom().nextInt(maxPasswordLength - minPasswordLength + 1) + minPasswordLength;

        PasswordGenerator passwordGenerator = new PasswordGenerator();

        // Attempt password generation until it meets requirements
        String password = "";
        do {
            password = passwordGenerator.generatePassword(passwordLength,
                    lowerCaseRule, upperCaseRule, digitRule, specialCharRule);
        } while (!password.matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[-_@$!%*?&])[A-Za-z\\d-_@$!%*?&]{6,12}$"));

        return password;
    }

}
