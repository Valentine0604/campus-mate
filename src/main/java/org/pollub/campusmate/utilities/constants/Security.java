package org.pollub.campusmate.utilities.constants;

import org.pollub.campusmate.utilities.security.Role;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collections;
import java.util.List;

public class Security {
    protected static final String SECRET_KEY = "d606b38b478abebe239aabe3393e8db60ac429a1013ed5688fa5d77169879c85";
    protected final static List<UserDetails> APPLICATION_USERS = List.of(
            new User(
                    "admin@admin.com",
                    "admin",
                    Collections.singleton(new SimpleGrantedAuthority(Role.ADMIN.name())
                    )
            ));

}
