package org.pollub.campusmate.utilities.security.config;

import org.passay.CharacterData;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;
import org.pollub.campusmate.utilities.constants.GeneratePassword;
import org.pollub.campusmate.utilities.constants.Pattern;

import java.security.SecureRandom;

@SuppressWarnings("deprecation")
public class PasswdGenerator {

    public static String generatePassword() {

        CharacterData specialCharacterData = new CharacterData() {
            @Override
            public String getErrorCode() {
                return GeneratePassword.ERROR_CODE;
            }

            @Override
            public String getCharacters() {
                return GeneratePassword.CHARACTERS;
            }
        };

        CharacterRule lowerCaseRule = new CharacterRule(EnglishCharacterData.LowerCase, 1);
        CharacterRule upperCaseRule = new CharacterRule(EnglishCharacterData.UpperCase, 1);
        CharacterRule digitRule = new CharacterRule(EnglishCharacterData.Digit, 1);
        CharacterRule specialCharRule = new CharacterRule(specialCharacterData, 1);

        int minPasswordLength = 8;
        int maxPasswordLength = 12;

        int passwordLength = new SecureRandom().nextInt(maxPasswordLength - minPasswordLength + 1) + minPasswordLength;

        PasswordGenerator passwordGenerator = new PasswordGenerator();

        String password = "";
        do {
            password = passwordGenerator.generatePassword(passwordLength,
                    lowerCaseRule, upperCaseRule, digitRule, specialCharRule);
        } while (!password.matches(String.valueOf(Pattern.PASSWORD_PATTERN)));

        return password;
    }
}
