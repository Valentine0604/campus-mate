package org.pollub.campusmate.utilities.security;

public enum Role {
    ADMIN("ROLE_ADMIN"),
    STUDENT("ROLE_STUDENT"),
    LECTURER("ROLE_LECTURER");

    private final String displayName;

    Role(String displayName){
        this.displayName = displayName;
    }

    public String getDisplayName(){
        return this.displayName;
    }
}