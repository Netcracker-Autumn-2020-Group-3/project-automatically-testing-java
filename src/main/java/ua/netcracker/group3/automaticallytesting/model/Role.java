package ua.netcracker.group3.automaticallytesting.model;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority { //TODO implement enum to user realization
    ADMIN,
    MANAGER,
    ENGINEER;

    @Override
    public String getAuthority() {
        return name();
    }
}
