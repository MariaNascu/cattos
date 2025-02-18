package com.company.cattos.enums;

import lombok.Getter;

@Getter
public enum Role {
    NULL("null"),
    MEMBER("member"),
    USER("user"),
    ADMIN("admin");

    private final String name;

    Role(String name) {
        this.name = name;

    }

    @Override
    public String toString() {
        return name;
    }

    public static Role convertStringToRole(String role){
        return Role.valueOf(role);
    }
}
