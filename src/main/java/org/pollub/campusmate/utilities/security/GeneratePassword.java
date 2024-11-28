package org.pollub.campusmate.utilities.security;

import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class GeneratePassword {

    public static String generatePassword() throws NoSuchAlgorithmException {
        CharacterRule LCR = new CharacterRule(EnglishCharacterData.LowerCase);
        LCR.setNumberOfCharacters(4);

        CharacterRule UCR = new CharacterRule(EnglishCharacterData.UpperCase);
        UCR.setNumberOfCharacters(3);

        CharacterRule DR = new CharacterRule(EnglishCharacterData.Digit);
        DR.setNumberOfCharacters(2);

        CharacterRule SCR = new CharacterRule(EnglishCharacterData.Special);
        SCR.setNumberOfCharacters(3);

        int passwordLength = SecureRandom.getInstanceStrong().nextInt(7) + 6;

        PasswordGenerator passwordGenerator = new PasswordGenerator();

        return passwordGenerator.generatePassword(passwordLength, LCR, UCR, DR, SCR); //todo: find non deprecated solution



    }
}
