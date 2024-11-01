package org.pollub.campusmate.constants;

public class Pattern {
    public static final java.util.regex.Pattern PASSWORD_PATTERN = java.util.regex.Pattern.compile("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{6,12}$");
    public static final java.util.regex.Pattern PHONE_NUMBER_PATTERN = java.util.regex.Pattern.compile("^\\+48\\s?\\d{3}\\s?\\d{3}\\s?\\d{3}$|^\\d{3}\\s?\\d{3}\\s?\\d{3}$");
}
