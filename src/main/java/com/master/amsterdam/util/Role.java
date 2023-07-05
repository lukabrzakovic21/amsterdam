package com.master.amsterdam.util;

public enum Role {
    ADMIN,
    CUSTOMER,
    VENDOR;

    public static Role fromString(String role) {
        return valueOf(role.toUpperCase());
    }

    public static boolean check(String role){
        if(role == null || role.isEmpty()) {
            return false;
        }

        for (Role type : Role.values()) {
            if(type.name().equalsIgnoreCase(role)) {
                return true;
            }
        }

        return false;
    }
}
