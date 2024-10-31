package org.pollub.campusmate.constants;

import java.util.regex.Pattern;

public class Constants {
    public static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{6,12}$");
}
